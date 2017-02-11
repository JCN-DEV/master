'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courseTech', {
                parent: 'entity',
                url: '/courseTechs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.courseTech.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courseTech/courseTechs.html',
                        controller: 'CourseTechController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courseTech');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('courseTech.detail', {
                parent: 'entity',
                url: '/courseTech/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.courseTech.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courseTech/courseTech-detail.html',
                        controller: 'CourseTechDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courseTech');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CourseTech', function($stateParams, CourseTech) {
                        return CourseTech.get({id : $stateParams.id});
                    }]
                }
            })
            .state('courseTech.new', {
                parent: 'courseTech',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/courseTech/courseTech-dialog.html',
                        controller: 'CourseTechDialogController',
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
                        $state.go('courseTech', null, { reload: true });
                    }, function() {
                        $state.go('courseTech');
                    })
                }]
            })
            .state('courseTech.edit', {
                parent: 'courseTech',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/courseTech/courseTech-dialog.html',
                        controller: 'CourseTechDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CourseTech', function(CourseTech) {
                                return CourseTech.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('courseTech', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('courseTech.delete', {
                parent: 'courseTech',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/courseTech/courseTech-delete-dialog.html',
                        controller: 'CourseTechDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CourseTech', function(CourseTech) {
                                return CourseTech.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('courseTech', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
