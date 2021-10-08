# ShatteredSoftware Contributing Guide

If you are interested in contributing here, I will assume that you already have
some kind of idea what you're doing. If you don't, welcome! We're happy to help
answer any questions you have on the [ShatteredSoftware Discord](
https://discord.gg/invite/zUbNX9t).

## Code Style and Design Guidelines

For Java, we try to stick to IntelliJ's default formatting. When in doubt, 
either ask what should be preferred, or go with what's already in the code.

For Kotlin, we try to follow the [Kotlin Coding Conventions](
https://kotlinlang.org/docs/coding-conventions.html), and use default IntelliJ
formating.

We also have a couple other preferences for code design that are more 
difficult to convey with a style guide. Most of these should be common sense,
but here are some that we think are important.

### Immutable Unless Marked Otherwise

A good example of this is `Vector3` and `MutableVector3`. Vector3 cannot be 
mutated, and `MutableVector3` is very clearly marked as mutable in its class
name.

### `public final` or `val` If Possible

Since immutability means less bugs and race conditions, default to allowing 
access to everything and mutation to nothing.

### Use the Most Restrictive Superclass

If an interface can be used over a concrete class (`Map<String, String>` versus
`LinkedHashMap<String, String>`), it should be used. Same thing for return 
types and generic variance.

### New Code is Kotlin

All new code in projects should be in Kotlin; we're in the process of moving
to be full-Kotlin projects.

### Avoid Side Effects Where Possible

Functions and methods should make their side effects clear, and should avoid
them if at all possible outside specific circumstances where they cannot be
avoided. In those cases they should be clearly marked and documented.

### Prefer Functional Solutions

We like functional programming, and places where solutions of that form are
possible with the same complexity as a procedural equivalent should be 
preferred. If functional solutions make things more complex or more difficult
to read, procedural solutions are allowed.

## Markdown

* Markdown should be wrapped at 80 characters so that it can be viewed in most
  common terminal environments. HTML, where necessary, and certain links longer
  than 80 characters are exempt from this rule. 
* Bullet points should be left aligned so that additional wrapped lines start
  with the same indentation as the text on the line above it. (Check the 
  source for this block for an example).
* Code should be fenced and tagged with the associated language.
* Otherwise, markdown should be readable and as beautiful as possible.

## Commit Format

We use [Gitmoji](https://gitmoji.dev) in colon-format, and then uppercase 
commits like this:

``` 
:white_check_mark: Add tests to Path3
:arrow_up: Update JUnit to 5.3.10
:sparkles: Add new Item system
```

Old commits may not follow this style; that is fine.

Commits should also be written in the imperative mood, as if giving a
command. Commits should be able to be read in the form "If applied, this
commit will `[commit message]`.", such as "If applied, this commit will
add tests to Path3."

Commit subject lines should also be limited to 50 characters. Lines in the 
commit bodies should be limited to 72 characters. Commit bodies should be 
provided where additional context is useful, but are not required.

If your commits are not in this format, you will be asked to rebase and put 
them in this format.

## Recognition

Thank you in advance for your contributions to our project. Anyone who has
contributed is welcome to request to be added to the contributors list by 
replying to the open Contributors issue.