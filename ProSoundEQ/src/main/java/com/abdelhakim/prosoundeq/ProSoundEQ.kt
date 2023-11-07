package com.abdelhakim.prosoundeq

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer
import android.media.audiofx.PresetReverb
import android.media.audiofx.Virtualizer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.abdelhakim.prosoundeq.databinding.ActivityProSoundEqBinding

class ProSoundEQ : AppCompatActivity() {

    private lateinit var   binding: ActivityProSoundEqBinding
    private var seekbarContainer: LinearLayout? = null
    private  var seekbarTv: LinearLayout? = null

    var seekbarIds: ArrayList<Int> = ArrayList()

    companion object {
        var equalizer: Equalizer? = null
        var reverb: PresetReverb? = null
        var bassBoost: BassBoost? = null
        var virtualizer: Virtualizer? = null
        var mainColor = Color.parseColor("#00ffee")
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProSoundEqBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.mainTheme)


        val typeface = ResourcesCompat.getFont(this,R.font.pro_sound_eq_freedom)
        val settings = getSharedPreferences("ProSoundEQ", 0)


        val presetSpinner = binding.presetSpinner
        val reverbSpinner = binding.reverbSpinner

        seekbarContainer = binding.seekbarContainer
        seekbarTv = binding.seekbarTv



        binding.logo.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.abdelhakim.mplayerlite")))
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.abdelhakim.mplayerlite")))
            }
        }

        val switchEq = binding.switchEq
        switchEq.isChecked = settings.getBoolean("ENABLED", false)

        if (equalizer != null) {
            equalizer!!.enabled = switchEq.isChecked
        }


        if (switchEq.isChecked) binding.blinder.visibility = View.INVISIBLE else binding.blinder.visibility = View.VISIBLE

        switchEq.setOnClickListener {
            if (equalizer != null && bassBoost!= null && virtualizer != null && reverb != null) {
                if (switchEq.isChecked) {
                    binding.blinder.visibility = View.INVISIBLE
                    equalizer!!.enabled = true
                    bassBoost!!.enabled = true
                    virtualizer!!.enabled = true
                    reverb!!.enabled = true
                    settings.edit().putBoolean("ENABLED", true).apply()
                } else {
                    binding.blinder.visibility = View.VISIBLE
                    equalizer!!.enabled = false
                    bassBoost!!.enabled = false
                    virtualizer!!.enabled = false
                    reverb!!.enabled = false
                    settings.edit().putBoolean("ENABLED", false).apply()
                }
            }
        }


        Log.d("logologo","until here work")

        binding.backButtonEA.setOnClickListener { finish() }




        val bassProgress = settings!!.getInt("BASS", 0)

        if (bassBoost != null) {
            if (bassBoost!!.strengthSupported) {
                bassBoost!!.setStrength((bassProgress * 15).toShort())
                bassBoost!!.enabled = true
            }
        }

        binding.bassSeekbar.progressDrawable.setTint(mainColor)
        binding.bassSeekbar.thumb.setTint(mainColor)
        binding.bassSeekbar.progress = bassProgress


        binding.bassSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (bassBoost != null) {
                    if (bassBoost!!.strengthSupported) {
                        bassBoost!!.setStrength(((progress * 15).toShort()))
                        bassBoost!!.enabled = true
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        val virtualizerProgress = settings.getInt("VIRTUALIZER", 0)


        if (virtualizer != null) {
            if (virtualizer!!.strengthSupported) {
                virtualizer!!.setStrength((virtualizerProgress * 15).toShort())
                virtualizer!!.enabled = true
            }
        }

        binding.virtualizerSeekbar.progressDrawable.setTint(mainColor)
        binding.virtualizerSeekbar.thumb.setTint(mainColor)
        binding.virtualizerSeekbar.progress = virtualizerProgress

        binding.virtualizerSeekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (virtualizer != null) {
                    if (virtualizer!!.strengthSupported) {
                        virtualizer!!.setStrength((progress * 15).toShort())
                        virtualizer!!.enabled = true
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // end of vitualizer




        if (equalizer != null) {
            val max = equalizer!!.bandLevelRange[1].toInt()
            val min = equalizer!!.bandLevelRange[0].toInt()


            for (index in 0 until equalizer!!.numberOfBands) {

                val verticalSeekbar =
                    ProSoundEQVerticalSeekbar(this@ProSoundEQ)
                seekbarIds.add(index, View.generateViewId())
                verticalSeekbar.max = max - min
                verticalSeekbar.tag = index
                //verticalSeekbar.progressDrawable = ContextCompat.getDrawable(this,R.drawable.seekbar_style_equalizer)
                //verticalSeekbar.thumb = ContextCompat.getDrawable(this, R.drawable.thumb_equalizer)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    verticalSeekbar.maxHeight = 1
                }
                verticalSeekbar.id = seekbarIds[index]
                verticalSeekbar.splitTrack = true



                verticalSeekbar.progressDrawable.setTint(mainColor)
                verticalSeekbar.thumb.setTint(mainColor)


                verticalSeekbar.progress = settings.getInt(index.toString(), equalizer!!.getBandLevel(index.toShort()).toInt() - min)

                verticalSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                        if (equalizer != null) {
                            if (verticalSeekbar.shouldChange) {
                                presetSpinner.setSelection(0)
                                settings.edit().putInt(seekBar.tag.toString(), progress).apply()
                                if (equalizer!!.enabled) {

                                    equalizer!!.setBandLevel((seekBar.tag as Int).toShort(), (progress + min).toShort())

                                }
                            }
                        }
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar) {}
                })
                val layoutParams: TableRow.LayoutParams = TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f)
                verticalSeekbar.layoutParams = layoutParams
                var title: String
                if (equalizer!!.getCenterFreq(index.toShort()) > 1000000) {
                    title =  "${equalizer!!.getCenterFreq(index.toShort()) / 1000000} kHz"
                } else {
                    title = "${equalizer!!.getCenterFreq(index.toShort()) / 1000 } "
                    title = title.substring(0, title.length - 2) + " Hz"
                }

                val textView = TextView(this@ProSoundEQ)
                textView.text = title
                textView.maxLines = 1
                textView.typeface = typeface
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                textView.setTextColor(ContextCompat.getColor(this,R.color.white))
                val params: TableRow.LayoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                textView.gravity = Gravity.CENTER
                textView.layoutParams = params
                seekbarTv!!.addView(textView)
                seekbarContainer!!.addView(verticalSeekbar)

            }

            val noOfPresets = equalizer!!.numberOfPresets

            val presets = arrayOfNulls<String>(noOfPresets + 1)
            presets[0] = "Custom"

            for (i in 0 until noOfPresets) { presets[i + 1] = equalizer!!.getPresetName(i.toShort()) }


            val spinnerAdapter: ArrayAdapter<String?> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, presets)
            presetSpinner.adapter = spinnerAdapter

            presetSpinner.setSelection(settings.getInt("PRESET", 1))

            presetSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {

                    if (equalizer != null) {
                        settings.edit().putInt("PRESET", position).apply()
                        Runnable {
                            if (position != 0) {
                                equalizer!!.usePreset((position - 1).toShort())
                                for (i in seekbarIds.indices) {
                                    Runnable {
                                        val seekbar =
                                            findViewById<ProSoundEQVerticalSeekbar>(seekbarIds[i])
                                        seekbar.shouldChange = false
                                        seekbar.progress = 1
                                        seekbar.progress = equalizer!!.getBandLevel(i.toShort()) - min
                                        seekbar.updateThumb()
                                    }.run()
                                }
                            }
                        }.run()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }








        val reverbAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("None", "Small Room", "Medium Room", "Large Room", "Medium Hall", "Large Hall", "Plate")
        )
        reverbSpinner.adapter = reverbAdapter
        reverbSpinner.setSelection(settings.getInt("REVERB", 0))


        reverbSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                if (reverb != null) {
                    Runnable {
                        reverb!!.preset = position.toShort()
                        reverb!!.enabled = true
                        settings.edit().putInt("REVERB", position).apply()
                    }.run()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    override fun onResume() {
        super.onResume()
        //overridePendingTransition(R.anim.zoom_enter, R.anim.none)

        val settings = getSharedPreferences("ProSoundEQ", 0)

        val virtualizerProgress = settings.getInt("VIRTUALIZER", 0)
        binding.virtualizerSeekbar.progress = virtualizerProgress

        val bassProgress = settings!!.getInt("BASS", 0)
        binding.bassSeekbar.progress = bassProgress


    }

    override fun onPause() {
        super.onPause()

        //overridePendingTransition(R.anim.none, R.anim.zoom_exit)

        val settings = getSharedPreferences("ProSoundEQ", 0).edit()

        settings.putInt("BASS", binding.bassSeekbar.progress)
        settings.putInt("VIRTUALIZER", binding.virtualizerSeekbar.progress)

        settings.apply()
    }


}


