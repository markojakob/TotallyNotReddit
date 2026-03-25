import { Routes } from '@angular/router';
import { Main } from './pages/main/main';
import { SubredditPage } from './pages/subreddit-page/subreddit-page';
import { CommentsPage } from './pages/comments-page/comments-page';

export const routes: Routes = [{
    path: '',
    component: Main
},
{
    path: 'r/:subredditName',
    component: SubredditPage
},
{
    path: 'r/:subredditname/comments/:postId/:postSlug?',
    component: CommentsPage
},
];
