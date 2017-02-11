'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('professorApplicationStatusLog', {
                parent: 'entity',
                url: '/professorApplicationStatusLogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.professorApplicationStatusLog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/professorApplicationStatusLog/professorApplicationStatusLogs.html',
                        controller: 'ProfessorApplicationStatusLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('professorApplicationStatusLog');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('professorApplicationStatusLog.detail', {
                parent: 'entity',
                url: '/professorApplicationStatusLog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.professorApplicationStatusLog.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/professorApplicationStatusLog/professorApplicationStatusLog-detail.html',
                        controller: 'ProfessorApplicationStatusLogDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('professorApplicationStatusLog');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProfessorApplicationStatusLog', function($stateParams, ProfessorApplicationStatusLog) {
                        return ProfessorApplicationStatusLog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('professorApplicationStatusLog.new', {
                parent: 'professorApplicationStatusLog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/professorApplicationStatusLog/professorApplicationStatusLog-dialog.html',
                        controller: 'ProfessorApplicationStatusLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    status: null,
                                    remarks: null,
                                    fromDate: null,
                                    toDate: null,
                                    cause: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('professorApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('professorApplicationStatusLog');
                    })
                }]
            })
            .state('professorApplicationStatusLog.edit', {
                parent: 'professorApplicationStatusLog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/professorApplicationStatusLog/professorApplicationStatusLog-dialog.html',
                        controller: 'ProfessorApplicationStatusLogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProfessorApplicationStatusLog', function(ProfessorApplicationStatusLog) {
                                return ProfessorApplicationStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('professorApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('professorApplicationStatusLog.delete', {
                parent: 'professorApplicationStatusLog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/professorApplicationStatusLog/professorApplicationStatusLog-delete-dialog.html',
                        controller: 'ProfessorApplicationStatusLogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProfessorApplicationStatusLog', function(ProfessorApplicationStatusLog) {
                                return ProfessorApplicationStatusLog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('professorApplicationStatusLog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
