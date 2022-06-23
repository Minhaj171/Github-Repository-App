package com.example.githubrepoapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.githubrepoapp.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.security.acl.Owner;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public class ItemPojo implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("node_id")
    @Expose
    private String nodeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("private")
    @Expose
    private Boolean _private;
    @SerializedName("owner")
    @Expose
    private UserPojo user;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("stargazers_count")
    @Expose
    private Integer starCount;

    protected ItemPojo(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        nodeId = in.readString();
        name = in.readString();
        fullName = in.readString();
        byte tmp_private = in.readByte();
        _private = tmp_private == 0 ? null : tmp_private == 1;
        description = in.readString();
        if (in.readByte() == 0) {
            starCount = null;
        } else {
            starCount = in.readInt();
        }
        lastUpdatedDate = in.readString();
    }

    public static final Creator<ItemPojo> CREATOR = new Creator<ItemPojo>() {
        @Override
        public ItemPojo createFromParcel(Parcel in) {
            return new ItemPojo(in);
        }

        @Override
        public ItemPojo[] newArray(int size) {
            return new ItemPojo[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @SerializedName("pushed_at")
    @Expose
    private String lastUpdatedDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean get_private() {
        return _private;
    }

    public void set_private(Boolean _private) {
        this._private = _private;
    }

    public UserPojo getUser() {
        return user;
    }

    public void setUser(UserPojo user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(nodeId);
        dest.writeString(name);
        dest.writeString(fullName);
        dest.writeByte((byte) (_private == null ? 0 : _private ? 1 : 2));
        dest.writeString(description);
        if (starCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(starCount);
        }
        dest.writeString(lastUpdatedDate);
    }

}
