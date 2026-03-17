import { Routes } from '@angular/router';
import { Main } from './pages/main/main';
import { SubredditPage } from './pages/subredditPage/subreddit-page';

export const routes: Routes = [{
    path: '',
    component: Main
},
{
    path: 'r/:subredditName',
    component: SubredditPage
}
];
