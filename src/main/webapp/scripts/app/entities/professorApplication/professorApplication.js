'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('professorApplication', {
                parent: 'entity',
                url: '/professorApplications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.professorApplication.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/professorApplication/professorApplications.html',
                        controller: 'ProfessorApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('professorApplication');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('professorApplication.detail', {
                parent: 'entity',
                url: '/professorApplication/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.professorApplication.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/professorApplication/professorApplication-detail.html',
                        controller: 'ProfessorApplicationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('professorApplication');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProfessorApplication', function($stateParams, ProfessorApplication) {
                        return ProfessorApplication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('professorApplication.new', {
                parent: 'professorApplication',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/professorApplication/professorApplication-dialog.html',
                        controller: 'ProfessorApplicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date: null,
                                    indexNo: null,
                                    status: null,
                                    resulationDate: null,
                                    agendaNumber: null,
                                    workingBreak: null,
                                    workingBreakStart: null,
                                    workingBreakEnd: null,
                                    disciplinaryAction: null,
                                    disActionCaseNo: null,
                                    disActionFileName: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('professorApplication', null, { reload: true });
                    }, function() {
                        $state.go('professorApplication');
                    })
                }]
            })
            .state('professorApplication.edit', {
                parent: 'professorApplication',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/professorApplication/professorApplication-dialog.html',
                        controller: 'ProfessorApplicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProfessorApplication', function(ProfessorApplication) {
                                return ProfessorApplication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('professorApplication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('professorApplication.delete', {
                parent: 'professorApplication',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/professorApplication/professorApplication-delete-dialog.html',
                        controller: 'ProfessorApplicationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProfessorApplication', function(ProfessorApplication) {
                                return ProfessorApplication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('professorApplication', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
