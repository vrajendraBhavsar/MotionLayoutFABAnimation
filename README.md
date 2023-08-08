# MotionLayoutFABAnimation

- A versatile FAB button and card animation which inherently involves stateful transformations of
  your compose views, and maintaining visual aesthetics. Animation plays a crucial role of
  providing:

    - Seamlessness
    - Smooth experience
    - Reentrant

### Types of Reveal animations

| CIRCULAR_REVEAL                                              | ROTATION_REVEAL                                              | SCALE_REVEAL                                              | SLIDE_IN_FROM_BOTTOM                                         |
|--------------------------------------------------------------|--------------------------------------------------------------|-----------------------------------------------------------|--------------------------------------------------------------|
| <img src="/media/content_circular.gif" width=80% height=80%> | <img src="/media/content_rotation.gif" width=81% height=81%> | <img src="/media/content_scale.gif" width=90% height=90%> | <img src="/media/content_slide_in.gif" width=68% height=68%> |

### Key features

* Smooth Transitions made with MotionLayout
* Aesthetical Animations
* Customizable Animations
* Performance-Optimized Animations
* Intuitive Animation Controls

### Layout ids to hook your FAB with predefined motion script

Below, you will find animation references along with their corresponding layout IDs that can be utilized to replicate the same effects.
| Animation | Without FAB transition effect                                    | With FAB transition effect (Initial state)                      | With FAB transition effect (Later state)                      |
|-----------|------------------------------------------------------------------|-----------------------------------------------------------------|---------------------------------------------------------------|
| layoutId  | mi_single_fab                                                    | mi_fab_start                                                    | mi_fab_end                                                    |
| Reference | <img src="/media/content_single_fab.png" width=100% height=100%> | <img src="/media/content_fab_start.png" width=100% height=100%> | <img src="/media/content_fab_end.png" width=100% height=100%> |

1. Pointer ```1``` demonstrates the implementation of a single fab without
   employing any cross-fade effects. Simply set **mi_single_fab** as a ```layoutId``` and It'll take care of your single fab
   animation.
2. Pointer ```2``` indicates the initial state of a composable that handles the fade-in transition
   of a FAB, gradually revealing it as it transitions into the second composable.
3. Pointer ```3``` indicates the later state of a composable which manages the fade-out transition
   for the previous image, smoothly hiding it while the new composable becomes visible.

### Attributes

| Attribute                        | Description                                                                                                                   |
|----------------------------------|-------------------------------------------------------------------------------------------------------------------------------|
| ```circularRevealAnimationVal``` | To handle circular reveal animation.                                                                                          |
| ```animateButtonVal```           | To manage the state of a FAB button.                                                                                          |
| ```cardComposable```             | Pass your custom composable, which will be integrated into a card content post-reveal animation.                              |
| ```fabComposable```              | Pass your custom composable, which will be shown as a Floating Action Button.                                                 |
| ```hideFabPostAnimationVal```    | To hide FAB once the animation is done.                                                                                       |
| ```fabAnimationDur```            | To manage the animation duration of FAB.                                                                                      |
| ```revealAnimDur```              | To manage the animation duration of reveal animation.                                                                         |
| ```fabCloseDelay```              | To adjust the closing animation duration in order to make the reveal animation smooth.                                        |
| ```animationType```              | Enum to set the animation type mentioned in the "Types of Reveal animations" section.                                         |
| ```overlayBackgroundColor```     | To pass color which would set as an overlay background                                                                        |
| ```isCancellable```              | To handle dialog closure upon external touch interaction.                                                                     |
| ```debugMode```                  | To enable or disable debug mode of Motion Layout. Set **NONE** to close debug mode and **SHOW_ALL** to enable debug mode.     |

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

3. How to Implement:
   At the outset, we require a few states to manage our animations smoothly which you can handle from
   your custom composable.

To handle circular reveal animation

```
val revealAnimation = remember { mutableStateOf(false) }
```

To manage the state of a FAB button

```
val animateButton = remember { mutableStateOf(false) }
```

To hide FAB post-completion of its animation

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

We'd be delighted to receive links to your projects showcasing our component in action! Drop
us an email at sales@mindinventory.com. If you have any questions or suggestions concerning our
work, we'd love to hear from you. Your feedback is important to us!

<a href="https://www.mindinventory.com/contact-us.php?utm_source=gthb&utm_medium=repo&utm_campaign=fabcardreveal">
<img src="https://github.com/Sammindinventory/MindInventory/blob/main/hirebutton.png" width="203" height="43"  alt="app development">
</a>


