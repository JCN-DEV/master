'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('professorApplicationEditLog', {
                parent: 'entity',
                url: '/professorApplicationEditLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.professorApplicationEditLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/professorApplicationEditLog/professorApplicationEditLogs.html',
                        controller: 'ProfessorApplicationEditLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('professorApplicationEditLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('professorApplicationEditLog.detail', {
                parent: 'entity',
                url: '/professorApplicationEditLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.professorApplicationEditLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/professorApplicationEditLog/professorApplicationEditLog-detail.html',
                        controller: 'ProfessorApplicationEditLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('professorApplicationEditLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProfessorApplicationEditLog', function($stateParams, ProfessorApplicationEditLog) {
                        return ProfessorApplicationEditLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('professorApplicationEditLog.new', {
                parent: 'professorApplicationEditLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/professorApplicationEditLog/professorApplicationEditLog-dialog.html',
                        controller: 'ProfessorApplicationEditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    status: null,
                                    remarks: null,
                                    date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('professorApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('professorApplicationEditLog');
                    })
                }]
            })
            .state('professorApplicationEditLog.edit', {
                parent: 'professorApplicationEditLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/professorApplicationEditLog/professorApplicationEditLog-dialog.html',
                        controller: 'ProfessorApplicationEditLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProfessorApplicationEditLog', function(ProfessorApplicationEditLog) {
                                return ProfessorApplicationEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('professorApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('professorApplicationEditLog.delete', {
                parent: 'professorApplicationEditLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/professorApplicationEditLog/professorApplicationEditLog-delete-dialog.html',
                        controller: 'ProfessorApplicationEditLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProfessorApplicationEditLog', function(ProfessorApplicationEditLog) {
                                return ProfessorApplicationEditLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('professorApplicationEditLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
