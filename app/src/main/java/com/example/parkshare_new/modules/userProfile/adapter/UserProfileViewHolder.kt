package com.example.parkshare_new.modules.userProfile.adapter

import android.view.View
import android.widget.TextView
import com.example.parkshare_new.R
import com.example.parkshare_new.models.Parking
import com.example.parkshare_new.models.Profile

class UserProfileViewHolder(val itemView: View) {
    var userName: TextView? = null
    var userNumOfUsed: TextView? = null
    var userNumOfPosts: TextView? = null
    var userNumOfHelps: TextView? = null
    var userPosts: Array<Parking>? = null
    var profile: Profile? = null

    init {
        userName = itemView.findViewById(R.id.userName)
        userNumOfPosts = itemView.findViewById(R.id.nPostsTxt)
        userNumOfUsed = itemView.findViewById(R.id.nUsedTxt)
        userNumOfHelps = itemView.findViewById(R.id.nHelpTxt)
    }

    fun bind(profile: Profile?) {
        this.profile = profile

        if (profile != null) {
            userName?.text = profile.userName
            userNumOfPosts?.text = profile.posts.size.toString()
            userNumOfHelps?.text = profile.numberOfHelps.toString()
            userNumOfUsed?.text = profile.numberOfUsed.toString()
        }
    }
}