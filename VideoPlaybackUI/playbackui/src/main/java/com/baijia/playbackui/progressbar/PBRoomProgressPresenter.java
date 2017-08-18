package com.baijia.playbackui.progressbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baijia.playbackui.R;
import com.baijia.playbackui.utils.StringUtils;
import com.baijiahulian.player.BJPlayerView;
import com.baijiahulian.player.bean.VideoItem;
import com.baijiahulian.player.playerview.IPlayerBottomContact;
import com.baijiahulian.player.playerview.IPlayerCenterContact;
import com.baijiahulian.player.playerview.IPlayerTopContact;

/**
 * Created by wangkangfei on 17/8/16.
 * 播放器控制条,整合所有控制视图
 */

public class PBRoomProgressPresenter implements IPlayerTopContact.TopView, IPlayerBottomContact.BottomView, IPlayerCenterContact.CenterView {
    //view
    private View view;
    private BJPlayerView mPlayerView;
    private ImageView ivStartPause, ivDefinition, ivRate, ivSwitchScreen;
    private TextView tvCurrent, tvTotal;
    private SeekBar sbMain;

    //data
    private int totalDuration; //总时长
    private int curDuration;  //当前时长

    public PBRoomProgressPresenter(View view, BJPlayerView playerView) {
        this.view = view;
        this.mPlayerView = playerView;
        initView(view);
        initListener();
    }

    private void initView(View view) {
        ivStartPause = (ImageView) view.findViewById(R.id.iv_pb_progress_start_pause);
        ivDefinition = (ImageView) view.findViewById(R.id.iv_pb_progress_definition);
        ivRate = (ImageView) view.findViewById(R.id.iv_pb_progress_rate);
        ivSwitchScreen = (ImageView) view.findViewById(R.id.iv_pb_progress_switch_screen);
        tvCurrent = (TextView) view.findViewById(R.id.tv_pb_progress_current_time);
        tvTotal = (TextView) view.findViewById(R.id.tv_pb_progress_total_time);
        sbMain = (SeekBar) view.findViewById(R.id.sb_pb_progress_main);
    }

    private void initListener() {
        ivStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayerView != null) {
                    if (mPlayerView.isPlaying()) {
                        mPlayerView.pauseVideo();
                    } else {
                        mPlayerView.playVideo();
                    }
                }
            }
        });
        ivSwitchScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayerView != null) {
                    mPlayerView.switchOrientation();
                }
            }
        });
        sbMain.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            boolean userTouch;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                userTouch = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (userTouch) {
                    int pos = seekBar.getProgress() * totalDuration / 100;
                    mPlayerView.seekVideo(pos);
                }
                userTouch = false;
            }
        });
    }

    @Override
    public void onBind(IPlayerTopContact.IPlayer player) {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setOrientation(int orientation) {

    }

    @Override
    public void onBufferingUpdate(int percent) {

    }

    @Override
    public void showProgressSlide(int delta) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showVolumeSlide(int volume, int maxVolume) {

    }

    @Override
    public void showBrightnessSlide(int brightness) {

    }

    @Override
    public void showError(int what, int extra) {

    }

    @Override
    public void showError(int code, String message) {

    }

    @Override
    public void showWarning(String warn) {

    }

    @Override
    public void onShow() {

    }

    @Override
    public void onHide() {

    }

    @Override
    public void onVideoInfoLoaded(VideoItem videoItem) {

    }

    @Override
    public boolean isDialogShowing() {
        return false;
    }

    @Override
    public void updateDefinition() {

    }

    @Override
    public void setOnBackClickListener(View.OnClickListener listener) {

    }

    @Override
    public void onBind(IPlayerCenterContact.IPlayer player) {

    }

    @Override
    public boolean onBackTouch() {
        return false;
    }

    @Override
    public void onBind(IPlayerBottomContact.IPlayer player) {

    }

    @Override
    public void setDuration(int duration) {
        totalDuration = duration;
        updateVideoPosition();
    }

    @Override
    public void setCurrentPosition(int position) {
        curDuration = position;
        updateVideoPosition();
    }

    @Override
    public void setIsPlaying(boolean isPlaying) {
        if (isPlaying) {
            ivStartPause.setImageResource(R.drawable.bjplayer_ic_back);
        } else {
            ivStartPause.setImageResource(R.drawable.pb_ic_exit);
        }
    }

    private void updateVideoPosition() {
        String durationText = StringUtils.formatDurationPB(totalDuration);
        String positionText = StringUtils.formatDurationPB(curDuration, totalDuration >= 3600);
        tvCurrent.setText(positionText);
        tvTotal.setText(durationText);
        sbMain.setProgress(totalDuration == 0 ? 0 : curDuration * 100 / totalDuration);
    }
}