package com.example.sankalp.mymiwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Sankalp on 7/4/2017.
 */

public class Family extends Fragment {
    private MediaPlayer mMediaPlayer;
    private AudioManager maudio;
    private Context mContext;
    private AudioManager.OnAudioFocusChangeListener maudiochangelist = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.
                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
                mMediaPlayer.start();
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
                releaseMediaPlayer();
        }

    };
    public Family() {
        // Required empty public constructor
    }
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        maudio = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<word> fam=new ArrayList<word>();
        fam.add(new word(R.drawable.family_father,"father","әpә",R.raw.family_father));
        fam.add(new word(R.drawable.family_mother,"mother","әṭa",R.raw.family_mother));
        fam.add(new word(R.drawable.family_son,"son","angsi",R.raw.family_son));
        fam.add(new word(R.drawable.family_daughter,"daughter","tune",R.raw.family_daughter));
        fam.add(new word(R.drawable.family_older_brother,"Older brother","taachi",R.raw.family_older_brother));
        fam.add(new word(R.drawable.family_younger_brother,"Younger brother","chalitti",R.raw.family_younger_brother));
        fam.add(new word(R.drawable.family_older_sister,"Older sister","teṭe",R.raw.family_older_sister));
        fam.add(new word(R.drawable.family_younger_sister,"Younger sister","teṭe",R.raw.family_younger_sister));
        fam.add(new word(R.drawable.family_grandmother,"grandmother","ama",R.raw.family_grandmother));
        fam.add(new word(R.drawable.family_grandfather,"grandfather","paapa",R.raw.family_grandfather));
        wordAdapter adapter = new wordAdapter(getActivity(), fam,R.color.category_family);
        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Get the {@link Word} object at the given position the user clicked on
                word word = fam.get(position);

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = maudio.requestAudioFocus(maudiochangelist,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getsong());

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }
    @Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            maudio.abandonAudioFocus(maudiochangelist);
        }
    }

}
