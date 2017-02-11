'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courseSubject', {
                parent: 'entity',
                url: '/courseSubjects',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.courseSubject.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courseSubject/courseSubjects.html',
                        controller: 'CourseSubjectController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courseSubject');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('courseSubject.detail', {
                parent: 'entity',
                url: '/courseSubject/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.courseSubject.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courseSubject/courseSubject-detail.html',
                        controller: 'CourseSubjectDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courseSubject');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CourseSubject', function($stateParams, CourseSubject) {
                        return CourseSubject.get({id : $stateParams.id});
                    }]
                }
            })
            .state('courseSubject.new', {
                parent: 'courseSubject',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/courseSubject/courseSubject-dialog.html',
                        controller: 'CourseSubjectDialogController',
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
                        $state.go('courseSubject', null, { reload: true });
                    }, function() {
                        $state.go('courseSubject');
                    })
                }]
            })
            .state('courseSubject.edit', {
                parent: 'courseSubject',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/courseSubject/courseSubject-dialog.html',
                        controller: 'CourseSubjectDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CourseSubject', function(CourseSubject) {
                                return CourseSubject.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('courseSubject', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('courseSubject.delete', {
                parent: 'courseSubject',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/courseSubject/courseSubject-delete-dialog.html',
                        controller: 'CourseSubjectDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CourseSubject', function(CourseSubject) {
                                return CourseSubject.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('courseSubject', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
