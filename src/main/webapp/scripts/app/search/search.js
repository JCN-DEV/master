'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('search', {
                parent: 'site',
                url: '/search/{q}',
                data: {
                    roles: [],
                    pageTitle: 'search.title'
                },
                views: {
                    'navbar@': {
                        templateUrl: 'scripts/components/navbar/navbar-search.html',
                        controller: 'NavbarSearchController'
                    },
                    'content@': {
                        templateUrl: 'scripts/app/search/search.html',
                        controller: 'SearchController'
                    }
                },
                resolve: {
                    searchTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('search');
                        $translatePartialLoader.addPart('job');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JobSearch', function($stateParams, JobSearch) {
                        return JobSearch.query({query: $stateParams.q});
                        //Employee.get({id : $stateParams.id});
                    }]
                }
            }).state('searchWithCategory', {
                parent: 'site',
                url: '/search/{id}/{q}',
                data: {
                    roles: [],
                    pageTitle: 'search.title'
                },
                views: {
                    'navbar@': {
                        templateUrl: 'scripts/components/navbar/navbar-search.html',
                        controller: 'NavbarSearchController'
                    },
                    'content@': {
                        templateUrl: 'scripts/app/search/search.html',
                        controller: 'SearchController'
                    }
                },
                resolve: {
                    searchTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('search');
                        $translatePartialLoader.addPart('job');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JobsByCategoryId', function($stateParams, JobsByCategoryId) {
                        return JobsByCategoryId.query({id: $stateParams.id});
                        //Employee.get({id : $stateParams.id});
                    }]
                }
            }).state('searchJobByCat', {
                parent: 'site',
                url: '/search/job/category/{cat}',
                data: {
                    roles: [],
                    pageTitle: 'search.title'
                },
                views: {
                    'navbar@': {
                        templateUrl: 'scripts/components/navbar/navbar-search.html',
                        controller: 'NavbarSearchController'
                    },
                    'content@': {
                        templateUrl: 'scripts/app/search/search.html',
                        controller: 'SearchController'
                    }
                },
                resolve: {
                    searchTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('search');
                        $translatePartialLoader.addPart('job');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CurJobSearchByCat', function($stateParams, CurJobSearchByCat) {
                        return CurJobSearchByCat.query({cat: $stateParams.cat});
                        //Employee.get({id : $stateParams.id});
                    }]
                }
            }).state('searchJobByLocation', {
                parent: 'site',
                url: '/search/job/location/{location}',
                data: {
                    roles: [],
                    pageTitle: 'search.title'
                },
                views: {
                    'navbar@': {
                        templateUrl: 'scripts/components/navbar/navbar-search.html',
                        controller: 'NavbarSearchController'
                    },
                    'content@': {
                        templateUrl: 'scripts/app/search/search.html',
                        controller: 'SearchController'
                    }
                },
                resolve: {
                    searchTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('search');
                        $translatePartialLoader.addPart('job');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CurJobSearchLocation', function($stateParams, CurJobSearchLocation) {
                       /* return CurJobSearchLocation.query({location: $stateParams.location}, function(result, headers) {
                            $scope.links = ParseLinks.parse(headers('link'));
                            $scope.jobs = result;
                            console.log(result);
                            $scope.total = headers('x-total-count');
                        });*/
                        return CurJobSearchLocation.query({location: $stateParams.location});
                        //Employee.get({id : $stateParams.id});
                    }]
                }
            }).state('searchJobByOrg', {
                parent: 'site',
                url: '/search/job/organization/{employer}',
                data: {
                    roles: [],
                    pageTitle: 'search.title'
                },
                views: {
                    'navbar@': {
                        templateUrl: 'scripts/components/navbar/navbar-search.html',
                        controller: 'NavbarSearchController'
                    },
                    'content@': {
                        templateUrl: 'scripts/app/search/search.html',
                        controller: 'SearchController'
                    }
                },
                resolve: {
                    searchTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('search');
                        $translatePartialLoader.addPart('job');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CurJobSearchEmployer', function($stateParams, CurJobSearchEmployer) {

                        return CurJobSearchEmployer.query({employer: $stateParams.employer});
                        //Employee.get({id : $stateParams.id});
                    }]
                }
            });
    });
