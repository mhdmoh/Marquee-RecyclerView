# Marquee-RecyclerView
A custom `RecyclerView` that implements the marquee behavior


![alt tag](https://github.com/mhdmoh/Marquee-RecyclerView/blob/main/screenshots/preview.gif)


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

and then in your activity :
- create a list of `MarqueeItem`
```
    List<MarqueeItem> items = new ArrayList<>();
```

- add your data to the list :
```
    items.add(new MarqueeItem("text", src));
```
    * the src can be a resource id from your assets or a url

- add the list to your MarqueeRecyclerView
```
    marqueeRecyclerView.setMarqueeItems(items);
```

## Manipulating The Main Background:

| Feature                         | Example                                                     |
| :---                            |                                                        :--- |
| set the background color        | app:main_background_color="@color/purple"                   |
| show or hide logo               | app:show_logo="true"                                        |
| set the logo src                | app:logo_src="@drawable/logo"                               |
| set the logo padding            | app:logo_padding="8dp"                                      |
| set the logo left padding       | app:logo_paddingLeft="8dp"                                  |
| set the logo right padding      | app:logo_paddingRight="8dp"                                 |
| set the logo top padding        | app:logo_paddingTop="8dp"                                   |
| set the logo bottom padding     | app:logo_paddingBottom="8dp"                                |
| set the spacing width           | app:spacing_width="0.12"                                    |

### Spacing Width :
You can change the spacing at the left and right of the marquee by using the attribute `spacing_width`,
you can set it's value between ```[0, 0.45]``` and it is mesured by the precentage of the width of the screen

![alt tag](https://github.com/mhdmoh/Marquee-RecyclerView/blob/main/screenshots/spacing-example.jpg)

### Styling :
You can choose from two styles ```[default_style , without_bottom_line]``` by using the attribute `marquee_style`
```
    app:marquee_style="without_bottom_line|default_style"
```
* example for the `default_style` :

![alt tag](https://github.com/mhdmoh/Marquee-RecyclerView/blob/main/screenshots/default_style.jpg)

* example for the `without_bottom_line` :

![alt tag](https://github.com/mhdmoh/Marquee-RecyclerView/blob/main/screenshots/without_bottom_line.jpg)

you can also hide the logo image by using the attribute `show_logo` :
```
    app:show_logo="true"
```
![alt tag](https://github.com/mhdmoh/Marquee-RecyclerView/blob/main/screenshots/no_logo.jpg)



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