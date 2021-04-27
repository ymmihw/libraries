package com.ymmihw.rxjava.retrofit.rx;

import java.util.List;
import com.ymmihw.rxjava.retrofit.models.Contributor;
import com.ymmihw.rxjava.retrofit.models.Repository;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubRxApi {

  /**
   * List GitHub repositories of user
   * 
   * @param user GitHub Account
   * @return GitHub repositories
   */
  @GET("users/{user}/repos")
  Observable<List<Repository>> listRepos(@Path("user") String user);

  /**
   * List Contributors of a GitHub Repository
   * 
   * @param user GitHub Account
   * @param repo GitHub Repository
   * @return GitHub Repository Contributors
   */
  @GET("repos/{user}/{repo}/contributors")
  Observable<List<Contributor>> listRepoContributors(@Path("user") String user,
      @Path("repo") String repo);

}
