package com.example.backend.Model;

import com.example.backend.Repository.UserRepository;
import com.example.backend.Repository.SubredditRepository;
import com.example.backend.Repository.PostRepository;
import com.example.backend.Repository.CommentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SeedData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection for all necessary repositories and your password encoder
    public SeedData(UserRepository userRepository,
                    SubredditRepository subredditRepository,
                    PostRepository postRepository,
                    CommentRepository commentRepository,
                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subredditRepository = subredditRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            System.out.println("🌱 Starting database seeding...");

            // Users
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@reddit.com");
            admin.setPasswordHash(passwordEncoder.encode("password123"));
            admin.setIsAdmin(true);
            userRepository.save(admin);

            User alice = new User();
            alice.setUsername("alice_dev");
            alice.setEmail("alice@reddit.com");
            alice.setPasswordHash(passwordEncoder.encode("password123"));
            userRepository.save(alice);

            User bob = new User();
            bob.setUsername("bob_gamer");
            bob.setEmail("bob@reddit.com");
            bob.setPasswordHash(passwordEncoder.encode("password123"));
            userRepository.save(bob);

            User carol = new User();
            carol.setUsername("carol_codes");
            carol.setEmail("carol@reddit.com");
            carol.setPasswordHash(passwordEncoder.encode("password123"));
            userRepository.save(carol);

            User dave = new User();
            dave.setUsername("dave_plays");
            dave.setEmail("dave@reddit.com");
            dave.setPasswordHash(passwordEncoder.encode("password123"));
            userRepository.save(dave);

            User eve = new User();
            eve.setUsername("eve_techie");
            eve.setEmail("eve@reddit.com");
            eve.setPasswordHash(passwordEncoder.encode("password123"));
            userRepository.save(eve);

            // Subreddits
            Subreddit programming = new Subreddit();
            programming.setName("programming");
            programming.setDescription("A community for world class software engineers and enthusiasts alike.");
            programming.setRules("1. Follow formatting guidelines.\n2. Be respectful.\n3. No spam or self-promotion.\n4. Use code blocks for code.");
            programming.setCreatedBy(admin);
            subredditRepository.save(programming);

            Subreddit gaming = new Subreddit();
            gaming.setName("gaming");
            gaming.setDescription("Your home for video game news, reviews, and community clips.");
            gaming.setRules("1. No console wars.\n2. Tag spoilers clearly.\n3. No piracy links.\n4. Be kind to new players.");
            gaming.setCreatedBy(admin);
            subredditRepository.save(gaming);

            Subreddit jff = new Subreddit();
            jff.setName("jff jfe");
            jff.setDescription("Just for fun — memes, shower thoughts, and random internet gold.");
            jff.setRules("1. Keep it fun.\n2. No hate speech.\n3. Credit original creators.");
            jff.setCreatedBy(alice);
            subredditRepository.save(jff);

            Subreddit webdev = new Subreddit();
            webdev.setName("webdev");
            webdev.setDescription("All things web development — frontend, backend, and everything in between.");
            webdev.setRules("1. Be constructive.\n2. No framework wars.\n3. Show your work.");
            webdev.setCreatedBy(carol);
            subredditRepository.save(webdev);

            Subreddit technology = new Subreddit();
            technology.setName("technology");
            technology.setDescription("News and discussion about the latest in tech.");
            technology.setRules("1. Link to original sources.\n2. No clickbait titles.\n3. Stay on topic.");
            technology.setCreatedBy(admin);
            subredditRepository.save(technology);

            // Posts - programming
            Post post1 = new Post();
            post1.setTitle("Why Spring Boot + Angular is a cheat code for projects");
            post1.setContent("Seriously, Java's type safety alongside JPA coupled with Angular's structural directives makes rapid prototyping absurdly fast. Change my mind.");
            post1.setUser(alice);
            post1.setSubreddit(programming);
            postRepository.save(post1);

            Post post2 = new Post();
            post2.setTitle("What's your go-to tech stack in 2026?");
            post2.setContent("I've been using Spring Boot + Angular for a while now, but curious what others are running. Anyone fully on microservices? Still on monoliths? Drop your stack below.");
            post2.setUser(carol);
            post2.setSubreddit(programming);
            postRepository.save(post2);

            Post post3 = new Post();
            post3.setTitle("Stop using var in JavaScript. Seriously.");
            post3.setContent("It's 2026 and I still see var in production codebases. Let and const exist for a reason. Scoping matters. Please, I'm begging you.");
            post3.setUser(eve);
            post3.setSubreddit(programming);
            postRepository.save(post3);

            Post post4 = new Post();
            post4.setTitle("Anyone else find recursion weirdly satisfying?");
            post4.setContent("There's something deeply elegant about a function that calls itself. Once it clicks, it really clicks. What was your recursion 'aha' moment?");
            post4.setUser(bob);
            post4.setSubreddit(programming);
            postRepository.save(post4);

            // Posts - gaming
            Post post5 = new Post();
            post5.setTitle("Is Elden Ring still worth playing in 2026?");
            post5.setContent("Missed out on it when it dropped. Looking to pick it up this week, should I commit or dive into something newer?");
            post5.setUser(bob);
            post5.setSubreddit(gaming);
            postRepository.save(post5);

            Post post6 = new Post();
            post6.setTitle("What game has the best open world of all time?");
            post6.setContent("Not just size — I mean density, atmosphere, secrets, and feeling like a living world. For me it's still Red Dead Redemption 2. Nothing comes close to the level of detail.");
            post6.setUser(dave);
            post6.setSubreddit(gaming);
            postRepository.save(post6);

            Post post7 = new Post();
            post7.setTitle("Hot take: turn-based combat is superior to action combat");
            post7.setContent("Hear me out. Turn-based requires actual strategy. You can't just mash buttons and dodge. Games like Baldur's Gate 3 prove that turn-based is peak RPG design.");
            post7.setUser(alice);
            post7.setSubreddit(gaming);
            postRepository.save(post7);

            Post post8 = new Post();
            post8.setTitle("Games that genuinely made you emotional");
            post8.setContent("I'll start: the ending of The Last of Us Part I. I sat in silence for 10 minutes after. What game hit you hardest?");
            post8.setUser(carol);
            post8.setSubreddit(gaming);
            postRepository.save(post8);

            // Posts - webdev
            Post post9 = new Post();
            post9.setTitle("CSS Grid vs Flexbox — when do you use which?");
            post9.setContent("I keep going back and forth. My rule of thumb: Flexbox for 1D layouts, Grid for 2D. But I see people doing wild things with just Flexbox. What's your approach?");
            post9.setUser(eve);
            post9.setSubreddit(webdev);
            postRepository.save(post9);

            Post post10 = new Post();
            post10.setTitle("Tailwind CSS is either the best or worst thing to happen to CSS");
            post10.setContent("No in-between. Either you love utility classes and ship fast, or you hate cluttered HTML and miss semantic class names. Where do you stand?");
            post10.setUser(carol);
            post10.setSubreddit(webdev);
            postRepository.save(post10);

            Post post11 = new Post();
            post11.setTitle("Show off your side project!");
            post11.setContent("Built something cool recently? Drop it below. Doesn't matter if it's polished — half-finished projects are welcome too. Let's see what the community is building.");
            post11.setUser(admin);
            post11.setSubreddit(webdev);
            postRepository.save(post11);

            // Posts - technology
            Post post12 = new Post();
            post12.setTitle("AI is changing software development faster than anyone predicted");
            post12.setContent("Two years ago I was skeptical. Now I use AI tools daily and my output has genuinely tripled. The junior dev role is changing forever. Is that good or bad?");
            post12.setUser(alice);
            post12.setSubreddit(technology);
            postRepository.save(post12);

            Post post13 = new Post();
            post13.setTitle("Why does every app need to be subscription-based now?");
            post13.setContent("I just want to buy software once and own it. The subscription model has completely taken over and I'm exhausted. Give me a one-time purchase or give me open source.");
            post13.setUser(dave);
            post13.setSubreddit(technology);
            postRepository.save(post13);

            // Posts - jff
            Post post14 = new Post();
            post14.setTitle("My code worked on the first try and I don't trust it");
            post14.setContent("Something is wrong. It compiled. It ran. It returned the correct result. I've been staring at it for 20 minutes waiting for it to break. It hasn't. I'm scared.");
            post14.setUser(eve);
            post14.setSubreddit(jff);
            postRepository.save(post14);

            Post post15 = new Post();
            post15.setTitle("Shower thought: dark mode is just the app wearing sunglasses");
            post15.setContent("That's it. That's the post.");
            post15.setUser(bob);
            post15.setSubreddit(jff);
            postRepository.save(post15);

            // Comments
            Comment c1 = new Comment();
            c1.setContent("Agreed! JPA handles so much boilerplate that writing raw SQL feels illegal now.");
            c1.setUser(bob);
            c1.setPost(post1);
            commentRepository.save(c1);

            Comment c2 = new Comment();
            c2.setContent("The Angular DI system alone is worth it. Once you understand it, every other framework feels barebone.");
            c2.setUser(carol);
            c2.setPost(post1);
            commentRepository.save(c2);

            Comment c3 = new Comment();
            c3.setContent("Counterpoint: the build times on Angular can be brutal on large projects. Still love it though.");
            c3.setUser(dave);
            c3.setPost(post1);
            commentRepository.save(c3);

            Comment c4 = new Comment();
            c4.setContent("Next.js + Postgres here. Can't recommend it enough for full stack.");
            c4.setUser(eve);
            c4.setPost(post2);
            commentRepository.save(c4);

            Comment c5 = new Comment();
            c5.setContent("Still on Rails + React. The old guard holds up surprisingly well.");
            c5.setUser(bob);
            c5.setPost(post2);
            commentRepository.save(c5);

            Comment c6 = new Comment();
            c6.setContent("Absolutely yes. It is an absolute masterpiece. Go in blind and don't look up guides!");
            c6.setUser(alice);
            c6.setPost(post5);
            commentRepository.save(c6);

            Comment c7 = new Comment();
            c7.setContent("It's worth it but be warned — the first 10 hours are a wall. Push through and it opens up completely.");
            c7.setUser(carol);
            c7.setPost(post5);
            commentRepository.save(c7);

            Comment c8 = new Comment();
            c8.setContent("RDR2 for me too. I still think about that game. The horse bond mechanic alone was more emotional than most entire games.");
            c8.setUser(alice);
            c8.setPost(post6);
            commentRepository.save(c8);

            Comment c9 = new Comment();
            c9.setContent("Baldur's Gate 3. Not even close. The world reacts to everything.");
            c9.setUser(eve);
            c9.setPost(post6);
            commentRepository.save(c9);

            Comment c10 = new Comment();
            c10.setContent("Grid for layout, Flexbox for components. That's the rule I live by.");
            c10.setUser(dave);
            c10.setPost(post9);
            commentRepository.save(c10);

            Comment c11 = new Comment();
            c11.setContent("I switched to Tailwind six months ago and never looked back. The speed is unreal.");
            c11.setUser(alice);
            c11.setPost(post10);
            commentRepository.save(c11);

            Comment c12 = new Comment();
            c12.setContent("I respect Tailwind but I can't look at those class strings. My brain refuses.");
            c12.setUser(bob);
            c12.setPost(post10);
            commentRepository.save(c12);

            Comment c13 = new Comment();
            c13.setContent("The Last of Us got me too. Also — What Remains of Edith Finch. Short but devastating.");
            c13.setUser(dave);
            c13.setPost(post8);
            commentRepository.save(c13);

            Comment c14 = new Comment();
            c14.setContent("AI pair programming tools have made me dramatically better at spotting my own patterns. It's like having a rubber duck that talks back.");
            c14.setUser(carol);
            c14.setPost(post12);
            commentRepository.save(c14);

            Comment c15 = new Comment();
            c15.setContent("I added a console.log. Still no idea why it works. Shipping it.");
            c15.setUser(dave);
            c15.setPost(post14);
            commentRepository.save(c15);

            Comment c16 = new Comment();
            c16.setContent("Delete it and write it again. That's the only way to be sure.");
            c16.setUser(carol);
            c16.setPost(post14);
            commentRepository.save(c16);

            System.out.println("🎯 Seeding complete! Login with any user: password123");
        } else {
            System.out.println("✨ Database already has data. Skipping seeder.");
        }
    }
}