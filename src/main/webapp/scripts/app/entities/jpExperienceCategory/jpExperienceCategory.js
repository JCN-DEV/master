'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jpExperienceCategory', {
                parent: 'entity',
                url: '/jpExperienceCategorys',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpExperienceCategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpExperienceCategory/jpExperienceCategorys.html',
                        controller: 'JpExperienceCategoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpExperienceCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jpExperienceCategory.detail', {
                parent: 'entity',
                url: '/jpExperienceCategory/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.jpExperienceCategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpExperienceCategory/jpExperienceCategory-detail.html',
                        controller: 'JpExperienceCategoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpExperienceCategory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpExperienceCategory', function($stateParams, JpExperienceCategory) {
                        return JpExperienceCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpExperienceCategory.new', {
                parent: 'jpExperienceCategory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpExperienceCategory/jpExperienceCategory-dialog.html',
                        controller: 'JpExperienceCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('jpExperienceCategory', null, { reload: true });
                    }, function() {
                        $state.go('jpExperienceCategory');
                    })
                }]
            })
            .state('jpExperienceCategory.edit', {
                parent: 'jpExperienceCategory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpExperienceCategory/jpExperienceCategory-dialog.html',
                        controller: 'JpExperienceCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['JpExperienceCategory', function(JpExperienceCategory) {
                                return JpExperienceCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('jpExperienceCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
