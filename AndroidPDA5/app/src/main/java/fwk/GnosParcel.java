package fwk;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

public class GnosParcel implements Parcelable {
    private JSONArray jsonArray;

    public void setJsonArray(JSONArray jsa) {
        this.jsonArray = jsa;
    }
    public JSONArray getJsonArray() {
        return jsonArray;
    }

    // Auto-generated Code
    protected GnosParcel(Parcel in) {
    }

    public static final Creator<GnosParcel> CREATOR = new Creator<GnosParcel>() {
        @Override
        public GnosParcel createFromParcel(Parcel in) {
            return new GnosParcel(in);
        }

        @Override
        public GnosParcel[] newArray(int size) {
            return new GnosParcel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
