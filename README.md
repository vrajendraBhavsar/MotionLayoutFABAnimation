# MotionLayoutFABAnimation

- A versatile FAB button and card animation which inherently involves stateful transformations of
  your compose views, and maintaining visual aesthetics. Animation plays a crucial role of
  providing:
    - Seamlessness
    - Smooth experience
    - Reentrant

### Types of Reveal animations

| Animation Type       | Preview                               |
|----------------------|---------------------------------------|
| CIRCULAR_REVEAL      | ![image](/media/content_circular.gif) |
| FADE_REVEAL          | ![image](/media/content_fade.gif)     |
| ROTATION_REVEAL      | ![image](/media/content_rotation.gif) |
| SCALE_REVEAL         | ![image](/media/content_scale.gif)    |
| SLIDE_IN_FROM_BOTTOM | ![image](/media/content_slide_in.gif) |

### Key features

* Smooth Transitions made with MotionLayout
* Aesthetical Animations
* Customizable Animations
* Performance-Optimized Animations
* Intuitive Animation Controls

### Layout ids to hook your FAB with predefined motion script

#Option:1 - Without FAB transition effect
You need to make use of ```layoutId``` modifier in order to hook your composable with the motion
scene and the rest will be taken care of!
| mi_single_fab |
|-----------------------------------------|
| ![image](/media/content_single_fab.png) |

#Option:2 - With FAB transition effect
In order to achieve a cross-fade animation effect with FAB, which would enable one image to get
transitioned into another during the FAB progression
| mi_fab_start | mi_fab_end |
|-----------------------------------------|-----------------------------------------|
| ![image](/media/content_fab_start.png)  | ![image](/media/content_fab_end.png)    |

### Attributes

| Attribute                        | Description                                                                                      |
|----------------------------------|--------------------------------------------------------------------------------------------------|
| ```circularRevealAnimationVal``` | To handle circular reveal animation                                                              |
| ```animateButtonVal```           | To manage the state of a FAB button                                                              |
| ```cardComposable```             | Pass your custom composable, which will be integrated into a card content post-reveal animation  |
| ```fabComposable```              | Pass your custom composable, which will be shown as a Floating Action Button                     |
| ```hideFabPostAnimationVal```    | To hide FAB once the animation is done                                                           |
| ```fabAnimationDur```            | To manage the animation duration of FAB                                                          |
| ```revealAnimDur```              | To manage the animation duration of reveal animation                                             |
| ```fabCloseDelay```              | To adjust closing animation duration in order to make reveal animation smooth                    |
| ```animationType```              | Enum to set the type of animation which is mentioned in the "Types of Reveal animations" section |
| ```overlayBackgroundColor```     | To pass color which would set as an overlay background                                           |
| ```isCancellable```              | To handle dialog closure upon external touch interaction                                         |
| ```debugMode```                  | To enable or disable debug mode of Motion Layout                                                 |

### How to use it? :thinking:

1. Add this to your root build.gradle at the end of repositories:

```
allprojects {
          repositories {
              maven { url 'https://jitpack.io' }
          }
}
```

2. Add the dependency in your app's build.gradle file:

```
dependencies {
    implementation 'com.github.Mindinventory:FabCardReveal:X.X.X'
}
```

3.  How to Implement:
   At the outset, we require few states to manage our animations smoothly which you can handle from
   your custom composable.

To handle circular reveal animation

```
val revealAnimation = remember { mutableStateOf(false) }
```

To manage the state of a FAB button

```
val animateButton = remember { mutableStateOf(false) }
```

To hide FAB post completion of it's animation

```
val animateButton = remember { mutableStateOf(false) }
```

```
CircularRevealAnimation(
  circularRevealAnimationVal = revealAnimation,
  animateButtonVal = animateButton,
  cardComposable = {
    YourCardComposableHandler {
      revealAnimation.value = false  //To trigger card closing animation
    }
},
fabComposable = {
  YourFabAnimationHandler()
},
hideFabPostAnimationVal = hideFabPostAnimation,
fabAnimationDur = 800,
revealAnimDur = 800,
fabCloseDelay = 800,  
animationType = AnimationType.CIRCULAR_REVEAL,
overlayBackgroundColor = MattePurple.copy(alpha = 0.8f)
isCancellable = true,
debugMode = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL)
)
```

# LICENSE!

FabCardReveal is [MIT-licensed](/LICENSE).

# Let us know!

We'd be delighted to receive links to your projects showcasing our component in action! Simply drop
us an email at sales@mindinventory.com. If you have any questions or suggestions concerning our
work, we'd love to hear from you. Your feedback is important to us!

<a href="https://www.mindinventory.com/contact-us.php?utm_source=gthb&utm_medium=repo&utm_campaign=fabcardreveal">
<img src="https://github.com/Sammindinventory/MindInventory/blob/main/hirebutton.png" width="203" height="43"  alt="app development">
</a>


