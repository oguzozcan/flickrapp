README: 

FlickrApp Project: Project is a showcase of using Flickr Api
Includes 3 calls to the Flickr Api. These are; Search Photo, Recent Photos and Photo Details

package name is: com.phillips.flickrapp

User Scenario:

App starts with most recent photos. User can scroll the list and open those photos on FullScreen. 
User can also search for a specific keyword and do the same thing in those photos.

Lists are endless, meaning there is Pagination on recyclerview so when user see the last item in the recyclerview a new call is made.

Full Screen photo view is implemented with a ViewPager so user can swipe between photos even in the FullScreen mode. User can see some photo details in this screen.

In Full Screen mode user can also zoom in and out to the photo.


3rd Party Libraries:

Picasso for showing images - It is memory efficient for big image files and it is easy to use

Retrofit: It turns my Http Api to a Java interface. Retrofit uses OkHttp as a Http client. Performance wise it is faster then both Volley and AsyncTask of android

Gson: To serialize jsons - retrofit uses gson and gsonconvertor libraries

Otto(Eventbus): I use otto with Retrofit to seperate my http calls from Activities and Fragments (which should only be responsible for drawing views to the screen)

LeakCanary: Is used to control the app against memory leaks in the development process.

In my implementation. PhotoService class is in the middle of the process. All api calls return to that class with my eventbus and distrubuted to the registered Activities/Fragments. So lifecycle or configurations of those classes doesnt effect http calls.

FlickrApp: my application class is responsible for getting ApiErrors and giving feedback to the user. Also picasso, otto, retrofit is initialized in that class.



