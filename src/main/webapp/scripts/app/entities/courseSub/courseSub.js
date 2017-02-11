'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courseSub', {
                parent: 'entity',
                url: '/courseSubs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.courseSub.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courseSub/courseSubs.html',
                        controller: 'CourseSubController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courseSub');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('courseSub.detail', {
                parent: 'entity',
                url: '/courseSub/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.courseSub.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courseSub/courseSub-detail.html',
                        controller: 'CourseSubDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courseSub');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CourseSub', function($stateParams, CourseSub) {
                        return CourseSub.get({id : $stateParams.id});
                    }]
                }
            })
            .state('courseSub.new', {
                parent: 'courseSub',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/courseSub/courseSub-dialog.html',
                        controller: 'CourseSubDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('courseSub', null, { reload: true });
                    }, function() {
                        $state.go('courseSub');
                    })
                }]
            })
            .state('courseSub.edit', {
                parent: 'courseSub',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/courseSub/courseSub-dialog.html',
                        controller: 'CourseSubDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CourseSub', function(CourseSub) {
                                return CourseSub.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('courseSub', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('courseSub.delete', {
                parent: 'courseSub',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/courseSub/courseSub-delete-dialog.html',
                        controller: 'CourseSubDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CourseSub', function(CourseSub) {
                                return CourseSub.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('courseSub', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
