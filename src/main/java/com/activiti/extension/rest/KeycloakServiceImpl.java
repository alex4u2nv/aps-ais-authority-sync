package com.activiti.extension.rest;

import com.activiti.extension.exceptions.CannotConnectException;
import com.activiti.extension.model.external.KeycloakGroup;
import com.activiti.extension.model.external.KeycloakToken;
import com.activiti.extension.model.external.KeycloakUser;
import com.google.common.base.Strings;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Alexander Mahabir
 *
 * This service connects to keycloak and featches the users need for the sync service.
 */
@Service("keycloakService")
public class KeycloakServiceImpl implements KeycloakService {

  private Retrofit retrofit;
  private KeycloakApi keycloakApi;
  private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakServiceImpl.class);

  @Value("${keycloak.auth-server-url}")
  private String keycloakUrl;

  @Value("${keycloak.enabled}")
  private Boolean keycloakEnabled;

  @Value("${kc.sync.serviceAccount}")
  private String serviceAccount;

  @Value("${kc.sync.serviceAccountPassword}")
  private String serviceAccountPassword;

  @Value("${keycloak.resource}")
  private String clientId;

  @Value("${keycloak.realm}")
  private String realm;

  private static final String GRANT_TYPE = "password";

  private KeycloakToken keycloakToken;


  /**
   * Initialize Rest Connector
   */
  @PostConstruct
  public void initializeConnector() {
    if (keycloakEnabled && !Strings.isNullOrEmpty(keycloakUrl)) {

      Interceptor authInterceptor = new AuthInterceptor();

      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(authInterceptor)
          .connectTimeout(1, TimeUnit.MINUTES)
          .readTimeout(5, TimeUnit.MINUTES)
          .writeTimeout(5, TimeUnit.MINUTES)
          .build();

      //If debugging is enabled, add debug interceptor for Rest Client.
      if (LOGGER.isTraceEnabled()) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(interceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .build();
      }
      String baseUrl = keycloakUrl.endsWith("/") ? keycloakUrl : keycloakUrl + "/";

      retrofit = new Retrofit.Builder()
          .baseUrl(baseUrl)
          .client(client)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
      keycloakApi = retrofit.create(KeycloakApi.class);
    }

    if (keycloakEnabled && Strings.isNullOrEmpty(keycloakUrl)) {
      LOGGER.error("Keycloak URL property keycloak.auth-server-url is empty");
    }
  }

  /**
   * Get a page of usrs
   *
   * @param first starting page -- page starts from 0
   * @param max maximum items per page.
   * @return List of Keycloak Users.
   */
  @Override
  public Optional<List<KeycloakUser>> getUsers(int first, int max) throws CannotConnectException {
    Optional<List<KeycloakUser>> users;
    try {

      users = Optional.of(keycloakApi.getUsers(realm, first, max).execute())
          .filter(r -> r.isSuccessful())
          .flatMap(r -> Optional.ofNullable(r.body()));

    } catch (IOException ioe) {
      LOGGER.error("Could not get users: {}\n {}", ioe.getMessage(),
          ExceptionUtils.getStackTrace(ioe));
      throw new CannotConnectException(ioe);
    }
    return users;
  }

  /**
   * Get all groups
   */
  @Override
  public Optional<Set<KeycloakGroup>> getGroups() throws CannotConnectException {
    Optional<Set<KeycloakGroup>> groups;
    try {
      groups = Optional.of(keycloakApi.getGroups(realm).execute())
          .filter(r -> r.isSuccessful())
          .flatMap(r -> Optional.ofNullable(r.body()));

    } catch (IOException ioe) {
      LOGGER.error("Could not get groups: {}\n {}", ioe.getMessage(),
          ExceptionUtils.getStackTrace(ioe));
      throw new CannotConnectException(ioe);

    }
    return groups;
  }

  /**
   * get all users belonging to a group
   */
  @Override
  public Optional<List<KeycloakUser>> getGroupMembers(String groupId, int first, int max)
      throws CannotConnectException {
    Optional<List<KeycloakUser>> members = Optional.empty();
    try {

      members = Optional.of(keycloakApi
          .getGroupMembers(realm, groupId, first, max).execute())
          .filter(r -> r.isSuccessful())
          .flatMap(r -> Optional.ofNullable(r.body()));

    } catch (IOException ioe) {
      LOGGER.error("Could not get groups members: {}\n {}", ioe.getMessage(),
          ExceptionUtils.getStackTrace(ioe));
      throw new CannotConnectException(ioe);

    }

    return members;
  }



  /**
   * Connect to Keycloak Instance and get Bearer information for subsquent requests
   */
  @Override
  public KeycloakToken connect() throws CannotConnectException {
    LOGGER.trace("Retrieving token");
    if (!keycloakEnabled) {
      throw new CannotConnectException("Keycloak not enabled");
    }

    if (keycloakEnabled && Strings.isNullOrEmpty(keycloakUrl)) {
      throw new CannotConnectException("Keycloak URL Not set");
    }

    if (retrofit == null) {
      throw new CannotConnectException("Connector not initialized");
    }

    KeycloakToken token;
    try {
      token = keycloakApi
          .authenticate(serviceAccount, serviceAccountPassword, GRANT_TYPE, clientId, realm)
          .execute()
          .body();
    } catch (IOException ioe) {
      LOGGER.error("Could not connect: {}\n {}", ioe.getMessage(),
          ExceptionUtils.getStackTrace(ioe));
      throw new CannotConnectException(ioe);
    }

    LOGGER.trace("Token: " + token);
    this.keycloakToken = token;
    return token;
  }

  /**
   * Default Constructor
   */
  public KeycloakServiceImpl() {

  }

  /**
   * Constructor used to support Mock Testing.
   */
  public KeycloakServiceImpl(Retrofit retrofit, KeycloakApi keycloakApi) {
    this.retrofit = retrofit;
    this.keycloakApi = keycloakApi;
  }


  /**
   * Fetch the total user count from keycloak
   */
  @Override
  public Integer getTotalUsers() throws CannotConnectException {
    LOGGER.trace("Getting Total Users");
    Integer count = 0;
    try {
      retrofit2.Response<Integer> response = keycloakApi.getTotalUsers(realm).execute();
      if (response.isSuccessful()) {
        count = response.body();

      } else {
        LOGGER.error("Keycloak Request Failed with code {} and message: \n{}", response.code(),
            response.errorBody());
      }
    } catch (IOException ioe) {
      LOGGER.error("Could not get users: {}\n {}", ioe.getMessage(),
          ExceptionUtils.getStackTrace(ioe));
      throw new CannotConnectException(ioe);
    }
    LOGGER.debug("Total Users: {}", count);
    return count;
  }

  /**
   *
   * @param first
   * @param max
   * @param search
   * @return
   * @throws CannotConnectException
   * @throws IOException
   */
  @Override
  public Optional<List<KeycloakUser>> getUsersWithSearch(int first, int max, String search)
      throws CannotConnectException {
    LOGGER.trace("Getting getUsersWithSearch Users");
    try {
      return Optional
          .ofNullable(this.keycloakApi.getUsers(realm, first, max, search).execute().body());
    } catch (IOException ioe) {
      LOGGER.error("Could not get users: {}\n {}", ioe.getMessage(),
          ExceptionUtils.getStackTrace(ioe));
      throw new CannotConnectException(ioe);
    }
  }

  /**
   *
   * @param first
   * @param max
   * @param firstName
   * @param lastName
   * @param username
   * @param email
   * @return
   * @throws CannotConnectException
   * @throws IOException
   */
  @Override
  public Optional<List<KeycloakUser>> getUsersWithFilters(int first, int max, String firstName,
      String lastName, String username, String email) throws CannotConnectException {

    try {
      return Optional.ofNullable(
          this.keycloakApi.getUsers(realm, first, max, username, lastName, firstName, email)
              .execute().body());
    } catch (IOException ioe) {
      LOGGER.error("Could not get users: {}\n {}", ioe.getMessage(),
          ExceptionUtils.getStackTrace(ioe));
      throw new CannotConnectException(ioe);
    }
  }

  /**
   * Get the keycloak bearer token
   */
  @Override
  public String getBearer() throws CannotConnectException {
    if (keycloakToken == null) {
      LOGGER.trace("Token is yet intiailized");
      this.connect();
    }
    String bearer = String.format("Bearer %s", keycloakToken.getAccess_token());
    LOGGER.trace("Authorization: {}", bearer);
    return bearer;
  }


  /**
   * Authentication Interceptor used to inject the bearer token into the header of a request.
   */
  private class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

      Request request = chain.request();
      if (!request.url().toString().contains("openid-connect/token")) {
        Request.Builder builder = request.newBuilder();

        builder.header("Authorization", getBearer());
        request = builder.build();

        Response response = chain.proceed(request);

        //Retry just incase our token expired.
        if (response.code() == 401) {
          synchronized (this) {
            LOGGER.debug("Reconnecting to Keycloak");
            connect();
            builder.header("Authorization", getBearer());
            request = builder.build();
            return chain.proceed(request);
          }
        }

        if (!response.isSuccessful()) {
          LOGGER.error("Keycloak Request Failed with code {} and message: {}", response.code(),
              response.message());
        }

        return response;
      }
      return chain.proceed(request);
    }
  }


}
