import { Routes } from '@angular/router';
import { Main } from './pages/main/main';
import { SubredditPage } from './pages/subreddit-page/subreddit-page';
import { CommentsPage } from './pages/comments-page/comments-page';
import { Register } from './pages/register/register';
import { Login } from './pages/login/login';
import { CreatePost } from './pages/create-post/create-post';
import { CreateSubreddit } from './pages/create-subreddit/create-subreddit';
import { EditPost } from './pages/edit-post/edit-post';
import { EditComment } from './pages/edit-comment/edit-comment';
import { ProfilePage } from './pages/profile-page/profile-page';

export const routes: Routes = [
  { path: '', component: Main },
  { path: 'r/:subredditName', component: SubredditPage },
  { path: 'r/:subredditName/comments/:postId/:postSlug', component: CommentsPage },

  { path: 'register', component: Register },
  { path: 'login', component: Login },
  { path: 'profile', component: ProfilePage },

  { path: 'create-post', component: CreatePost },
  { path: 'create-subreddit', component: CreateSubreddit },

  { path: 'posts/:postId/edit', component: EditPost },
  { path: 'comments/:commentId/edit', component: EditComment },
];