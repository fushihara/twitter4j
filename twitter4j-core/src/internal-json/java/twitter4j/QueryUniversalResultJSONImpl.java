/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package twitter4j;

import twitter4j.conf.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * A data class representing search API response
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
/*package*/ final class QueryUniversalResultJSONImpl extends TwitterResponseImpl implements QueryUniversalResult, java.io.Serializable {

    private static final long serialVersionUID = -5359566235429947157L;
    private List<Status> tweets = new ArrayList<Status>();
    private List<User> userGallerys = new ArrayList<User>();

    /*package*/ QueryUniversalResultJSONImpl(HttpResponse res, Configuration conf) throws TwitterException {
        super(res);
        JSONObject json = res.asJSONObject();
        try {
            JSONArray array = json.getJSONArray("modules");
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            for (int i = 0; i < array.length(); i++) {
                JSONObject module = array.getJSONObject(i);
                if( module.has("user_gallery") ){
                    JSONArray userGallerys = module.getJSONObject("user_gallery").getJSONArray("data");
                    for (int j = 0; j < userGallerys.length(); j++) {
                        JSONObject userData = userGallerys.getJSONObject(j).getJSONObject("data");
                        User user = new UserJSONImpl(userData);
                        this.userGallerys.add(user);
                    }
                }else if(module.has("status")){
                    JSONObject tweet = module.getJSONObject("status").getJSONObject("data");
                    tweets.add(new StatusJSONImpl(tweet, conf));
                }
            }
        } catch (JSONException jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    /*package*/ QueryUniversalResultJSONImpl(Query query) {
        super();
        tweets = new ArrayList<Status>(0);
        userGallerys = new ArrayList<User>(0);
    }


    @Override
    public List<Status> getTweets() {
        return tweets;
    }

    @Override
    public List<User> getUserGallery() {
        return userGallerys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryUniversalResult that = (QueryUniversalResult) o;

        if (tweets != null ? !tweets.equals(that.getTweets()) : that.getTweets() != null)
            return false;
        if (tweets != null ? !tweets.equals(that.getUserGallery()) : that.getUserGallery() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (tweets != null ? tweets.hashCode() : 0);
        result = 31 * result + (userGallerys != null ? userGallerys.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QueryUniversalResultJSONImpl{" +
                ", tweets=" + tweets +
                ", userGallerys=" + userGallerys +
                '}';
    }
}
