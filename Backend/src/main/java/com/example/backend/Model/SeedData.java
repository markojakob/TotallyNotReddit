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
        // Only run if the database has no users to prevent messy duplicate data
        if (userRepository.count() == 0) {
            System.out.println("🌱 Starting database seeding...");

            // 1. Create Mock Users (Hashing passwords so you can actually log into them via frontend)
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


            // 2. Create Mock Subreddits
            Subreddit programming = new Subreddit();
            programming.setName("programming");
            programming.setDescription("A community for world class software engineers and enthusiasts alike.");
            programming.setRules("1. Follow formatting guidelines. 2. Be respectful.");
            programming.setCreatedBy(admin);
            subredditRepository.save(programming);

            Subreddit gaming = new Subreddit();
            gaming.setName("gaming");
            gaming.setDescription("Your home for video game news, reviews, and community clips.");
            gaming.setRules("1. No console wars. 2. Tag spoilers clearly.");
            gaming.setCreatedBy(admin);
            subredditRepository.save(gaming);


            // 3. Create Mock Posts
            Post post1 = new Post();
            post1.setTitle("Why Spring Boot + Angular is a cheat code for projects");
            post1.setContent("Seriously, Java's type safety alongside JPA coupled with Angular's structural directives makes rapid prototyping absurdly fast. Change my mind.");
            post1.setUser(alice);
            post1.setSubreddit(programming);
            post1.setScore(42);
            postRepository.save(post1);

            Post post2 = new Post();
            post2.setTitle("Is Elden Ring still worth playing in 2026?");
            post2.setContent("Missed out on it when it dropped. Looking to pick it up this week, should I commit or dive into something newer?");
            post2.setUser(bob);
            post2.setSubreddit(gaming);
            post2.setScore(112);
            postRepository.save(post2);


            // 4. Create Mock Comments
            Comment comment1 = new Comment();
            comment1.setContent("Agreed! JPA handles so much boilerplate that writing raw SQL feels illegal now.");
            comment1.setUser(bob);
            comment1.setPost(post1);
            commentRepository.save(comment1);

            Comment comment2 = new Comment();
            comment2.setContent("Absolutely, yes. It is an absolute masterpiece. Go in blind and don't look up guides!");
            comment2.setUser(alice);
            comment2.setPost(post2);
            commentRepository.save(comment2);

            System.out.println("🎯 Database seeding complete! Admin login: admin / password123");
        } else {
            System.out.println("✨ Database already has data. Skipping seeder.");
        }
    }
}