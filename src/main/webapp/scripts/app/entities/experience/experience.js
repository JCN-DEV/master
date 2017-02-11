'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('experience', {
                parent: 'entity',
                url: '/experiences',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.experience.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/experience/experiences.html',
                        controller: 'ExperienceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('experience');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('experience.detail', {
                parent: 'entity',
                url: '/experience/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.experience.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/experience/experience-detail.html',
                        controller: 'ExperienceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('experience');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Experience', function($stateParams, Experience) {
                        return Experience.get({id : $stateParams.id});
                    }]
                }
            })
            .state('experience.new', {
                parent: 'experience',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.experience.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/experience/experience-form.html',
                        controller: 'ExperienceFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('experience');
                        return $translate.refresh();
                    }]

                }

            })
            .state('experience.edit', {
                parent: 'experience',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.experience.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/experience/experience-form.html',
                        controller: 'ExperienceFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('experience');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Experience', function($stateParams, Experience) {
                        return Experience.get({id : $stateParams.id});
                    }]

                }

            })
            .state('experience.delete', {
                parent: 'experience',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/experience/experience-delete-dialog.html',
                        controller: 'ExperienceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Experience', function(Experience) {
                                return Experience.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('experience', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
