package es.esy.playdotv.update;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

public final class AutoUpdate {
	
	public static void updateData(){
		String JSON = getGithubJSON();
		
		JSONObject jobj1 = new JSONObject(JSON);
		System.out.println(jobj1.getString("html_url"));
		System.out.println(jobj1.getBoolean("draft"));
		
	}
	
	private static String getGithubJSON(){
		URL url;
		String line;
		StringBuilder resultString = new StringBuilder();

		try{
			url = new URL("https://api.github.com/repos/ShardBytes/library-book-scanner/releases/latest");
			URLConnection connection = url.openConnection();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			while((line = reader.readLine()) != null){
				resultString.append(line);
			}
			reader.close();

		}catch(IOException e){
			System.err.println("Cannot download newest Github data.");
		}
		return resultString.toString();

	}
	
	public static final class GithubData{
		
		/*
		 * Release
		 */
		private static String release_url;
		private static String release_id;
		private static String release_version;
		private static String release_branch;
		private static String release_name;
		private static boolean release_isPrerelease;
		
		/*
		 * Author
		 */
		private static String author_name;
		private static String author_id;
		private static String author_avatar_url;
		private static String author_url;
		private static String author_organisations;
		private static String author_repos;
		private static String author_subscriptions;
		private static String author_type;
		private static boolean author_isAdmin;
		
		/*
		 * Assets
		 */
		private static String assets_url;
		private static String assets_id;
		private static String assets_name;
		private static String assets_label;
		private static String assets_size;
		private static String assets_download_count;
		private static String assets_download_url;
		
		/*
		 * Getters
		 */
		public static String getRelease_url() {
			return release_url;
		}
		public static String getRelease_id() {
			return release_id;
		}
		public static String getRelease_version() {
			return release_version;
		}
		public static String getRelease_branch() {
			return release_branch;
		}
		public static String getRelease_name() {
			return release_name;
		}
		public static boolean getRelease_isPrerelease() {
			return release_isPrerelease;
		}
		public static String getAuthor_name() {
			return author_name;
		}
		public static String getAuthor_id() {
			return author_id;
		}
		public static String getAuthor_avatar_url() {
			return author_avatar_url;
		}
		public static String getAuthor_url() {
			return author_url;
		}
		public static String getAuthor_organisations() {
			return author_organisations;
		}
		public static String getAuthor_repos() {
			return author_repos;
		}
		public static String getAuthor_subscriptions() {
			return author_subscriptions;
		}
		public static String getAuthor_type() {
			return author_type;
		}
		public static boolean getAuthor_isAdmin() {
			return author_isAdmin;
		}
		public static String getAssets_url() {
			return assets_url;
		}
		public static String getAssets_id() {
			return assets_id;
		}
		public static String getAssets_name() {
			return assets_name;
		}
		public static String getAssets_label() {
			return assets_label;
		}
		public static String getAssets_size() {
			return assets_size;
		}
		public static String getAssets_download_count() {
			return assets_download_count;
		}
		public static String getAssets_download_url() {
			return assets_download_url;
		}
		
		/*
		 * Setters
		 */
		static void setRelease_url(String release_url) {
			GithubData.release_url = release_url;
		}
		static void setRelease_id(String release_id) {
			GithubData.release_id = release_id;
		}
		static void setRelease_version(String release_version) {
			GithubData.release_version = release_version;
		}
		static void setRelease_branch(String release_branch) {
			GithubData.release_branch = release_branch;
		}
		static void setRelease_name(String release_name) {
			GithubData.release_name = release_name;
		}
		static void setRelease_isPrerelease(boolean release_isPrerelease) {
			GithubData.release_isPrerelease = release_isPrerelease;
		}
		static void setAuthor_name(String author_name) {
			GithubData.author_name = author_name;
		}
		static void setAuthor_id(String author_id) {
			GithubData.author_id = author_id;
		}
		static void setAuthor_avatar_url(String author_avatar_url) {
			GithubData.author_avatar_url = author_avatar_url;
		}
		static void setAuthor_url(String author_url) {
			GithubData.author_url = author_url;
		}
		static void setAuthor_organisations(String author_organisations) {
			GithubData.author_organisations = author_organisations;
		}
		static void setAuthor_repos(String author_repos) {
			GithubData.author_repos = author_repos;
		}
		static void setAuthor_subscriptions(String author_subscriptions) {
			GithubData.author_subscriptions = author_subscriptions;
		}
		static void setAuthor_type(String author_type) {
			GithubData.author_type = author_type;
		}
		static void setAuthor_isAdmin(boolean author_isAdmin) {
			GithubData.author_isAdmin = author_isAdmin;
		}
		static void setAssets_url(String assets_url) {
			GithubData.assets_url = assets_url;
		}
		static void setAssets_id(String assets_id) {
			GithubData.assets_id = assets_id;
		}
		static void setAssets_name(String assets_name) {
			GithubData.assets_name = assets_name;
		}
		static void setAssets_label(String assets_label) {
			GithubData.assets_label = assets_label;
		}
		static void setAssets_size(String assets_size) {
			GithubData.assets_size = assets_size;
		}
		static void setAssets_download_count(String assets_download_count) {
			GithubData.assets_download_count = assets_download_count;
		}
		static void setAssets_download_url(String assets_download_url) {
			GithubData.assets_download_url = assets_download_url;
		}
		
	}
	
}
