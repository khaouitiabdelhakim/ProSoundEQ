# ProSoundEQ - An Audio Equalization Library for Android

![ProSoundEQ](https://github.com/khaouitiabdelhakim/ProSoundEQ/blob/master/picture.png)

ProSoundEQ is a powerful Android library developed by Abdelhakim Khaouiti, designed to enhance your audio experience by providing audio equalization, bass boost, virtualizer, and reverb settings. This library is ideal for developers working on music-related Android applications, providing a seamless way to customize audio playback settings.

## Author

- **Author:** Abdelhakim Khaouiti
- **GitHub:** [khaouitiabdelhakim](https://github.com/khaouitiabdelhakim)

# Stats
![Alt](https://repobeats.axiom.co/api/embed/e52a8046df186f5d566c5905120c18864c53923b.svg "Repobeats analytics image")

## Last Modified

- **Last Modified:** 2023-12-31

## How to Use

To integrate ProSoundEQ into your Android project, follow these steps:

**Step 1. Add the JitPack Repository and Dependencies**

Add the JitPack repository to your root build.gradle file:

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Add the ProSoundEQ library as a dependency in your app's build.gradle file. Replace `Tag` with the specific release version you want to use (latest version: [![](https://jitpack.io/v/khaouitiabdelhakim/ProSoundEQ.svg)](https://jitpack.io/#khaouitiabdelhakim/ProSoundEQ)):

```groovy
dependencies {
    implementation 'com.github.khaouitiabdelhakim:ProSoundEQ:Tag'
}
```

**Step 2. Initialize ProSoundEQSettings**

Initialize the ProSoundEQSettings object to manage audio effects settings. This should be done after preparing the MediaPlayer:

```kotlin
// Initialize the MediaPlayer with the raw resource
mediaPlayer = MediaPlayer.create(this, R.raw.song)

// Set a listener for when the media file is ready
mediaPlayer?.setOnPreparedListener {
    // Start playing the song when it's prepared
    mediaPlayer?.start()
    ProSoundEQSettings.setColor("#34ebb1")
    ProSoundEQSettings.init(mediaPlayer!!.audioSessionId)
}

// Opening the equalizer
startActivity(Intent(this, ProSoundEQ::class.java))
```

**Note:** Ensure that you have the necessary permissions in your AndroidManifest.xml to access audio settings.

Feel free to explore and customize ProSoundEQ to suit your application's audio requirements. If you encounter issues, have suggestions for improvement, or want to contribute, we encourage you to get involved.

## Acknowledgments

We express our appreciation to the open-source community for their valuable contributions.

## License

```
Copyright 2024 KHAOUITI ABDELHAKIM

Licensed under the MIT License
You may obtain a copy of the License at

http://opensource.org/licenses/MIT

Unless required by applicable law or agreed to in writing, software
distributed under the MIT License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the MIT License.
```


ProSoundEQ provides a comprehensive solution for integrating advanced audio settings into your Android applications. Use it responsibly in your projects while respecting its authorship.
