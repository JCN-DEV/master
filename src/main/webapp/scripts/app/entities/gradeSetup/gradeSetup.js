'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('gradeSetup', {
                parent: 'entity',
                url: '/gradeSetups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.gradeSetup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gradeSetup/gradeSetups.html',
                        controller: 'GradeSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('gradeSetup');
                        $translatePartialLoader.addPart('gradeClass');
                        $translatePartialLoader.addPart('gradeType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('gradeSetup.detail', {
                parent: 'entity',
                url: '/gradeSetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.gradeSetup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gradeSetup/gradeSetup-detail.html',
                        controller: 'GradeSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('gradeSetup');
                        $translatePartialLoader.addPart('gradeClass');
                        $translatePartialLoader.addPart('gradeType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GradeSetup', function($stateParams, GradeSetup) {
                        return GradeSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('gradeSetup.new', {
                parent: 'gradeSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/gradeSetup/gradeSetup-dialog.html',
                        controller: 'GradeSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    no: null,
                                    details: null,
                                    gradeClass: null,
                                    type: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('gradeSetup', null, { reload: true });
                    }, function() {
                        $state.go('gradeSetup');
                    })
                }]
            })
            .state('gradeSetup.edit', {
                parent: 'gradeSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/gradeSetup/gradeSetup-dialog.html',
                        controller: 'GradeSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GradeSetup', function(GradeSetup) {
                                return GradeSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('gradeSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('gradeSetup.delete', {
                parent: 'gradeSetup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/gradeSetup/gradeSetup-delete-dialog.html',
                        controller: 'GradeSetupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['GradeSetup', function(GradeSetup) {
                                return GradeSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('gradeSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
