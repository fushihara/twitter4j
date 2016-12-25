package twitter4j;

import javax.tools.JavaCompiler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VoteJSONImpl extends TwitterResponseImpl implements java.io.Serializable {
    private int durationMinutes;
    private java.util.Date endTime;
    private List<String> choice = new ArrayList<String>();
    private List<Integer> count = new ArrayList<Integer>();

    public static boolean hasVote(JSONObject json) {
        try {
            if(!json.has("card")){
                return false;
            }
            if(!json.getJSONObject("card").has("binding_values")){
                return false;
            }
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    public VoteJSONImpl(JSONObject json) throws TwitterException {
        super();
        this.init(json);
    }

    private void init(JSONObject json) throws TwitterException {
        try {
            json = json.getJSONObject("card").getJSONObject("binding_values");
            this.durationMinutes = Integer.parseInt(json.getJSONObject("duration_minutes").getString("string_value"));
            // "2016-12-30T15:58:02Z"
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
            try {
                this.endTime = sdf.parse(json.getJSONObject("end_datetime_utc").getString("string_value"));
            } catch (ParseException e) {
                throw new TwitterException("", e);
            }
            for (int i = 1; i <= 4; i++) {
                if(!json.has("choice"+i+"_label")){
                    break;
                }
                String label = json.getJSONObject("choice"+i+"_label").getString("string_value");
                String countString =json.getJSONObject("choice"+i+"_count").getString("string_value");
                int count = Integer.parseInt(countString);
                this.choice.add(label);
                this.count.add(count);
            }
        } catch (JSONException e) {
            throw new TwitterException("", e);
        }
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public Date getEndTime() {
        return endTime;
    }

    public List<String> getChoice() {
        return choice;
    }

    public List<Integer> getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "VoteJSONImpl{" +
                "durationMinutes=" + durationMinutes +
                ", endTime=" + endTime +
                ", choice=" + choice +
                ", count=" + count +
                '}';
    }
}
