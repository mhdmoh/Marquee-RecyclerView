# Marquee-RecyclerView
A custom `RecyclerView` that implements the marquee behavior
<p align="center">
  <img src="https://github.com/mhdmoh/Marquee-RecyclerView/main/screenshots/preview.gif?raw=true" alt="Sublime's custom image"/>
</p>

## Installation
1. first add this in your root build.gradle at the end of repositories:

```js
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2.  then add the dependency to your app build.gradle
```js
dependencies {
         implementation 'com.github.mhdmoh:Marquee-RecyclerView:[Latest-Version]'
	}
```

## How to use
add the view to your layout from the xml
```
        <mhdmoh.marquee_recyclerview.MarqueeRecyclerView
        android:id="@+id/marquee_recycler_view"
        android:layout_width="match_parent"
        android:layout_height="?android:actionBarSize"
        app:layout_constraintBottom_toBottomOf="parent"
        app:logo_padding="12dp"
        app:spacing_width="0.12" />

```
### Manipulating The Main Background:

| Feature                         | Example                                                     |
| :---                            |                                                        :--- |
| set the background color        | app:main_background_color="@color/purple"                   |
| show or hide logo               | app:show_logo="true"                                        |
| set the logo src                | app:logo_src="@drawable/logo"                               |
| set the logo padding            | app:logo_padding="8dp"                                      |
| set the logo left padding       | app:logo_paddingLeft="8dp"                                  |
| set the logo right padding      | app:logo_paddingRight="8dp"                                 |
| set the logo top padding        | app:logo_paddingTop="8dp"                                   |
| set the logo bottom padding     | app:logo_paddingBottm="8dp"                                 |

<br/>

<h1 id="license">License :page_facing_up:</h1>

Copyright 2020 Philipp Jahoda

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

> http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

<br/>