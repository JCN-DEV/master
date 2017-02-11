'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('designation', {
                parent: 'entity',
                url: '/designations',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.designation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/designation/designations.html',
                        controller: 'DesignationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('designation');
                        $translatePartialLoader.addPart('designationType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('designation.detail', {
                parent: 'entity',
                url: '/designation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.designation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/designation/designation-detail.html',
                        controller: 'DesignationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('designation');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Designation', function($stateParams, Designation) {
                        return Designation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('designation.new', {
                parent: 'designation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/designation/designation-dialog.html',
                        controller: 'DesignationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    type: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('designation', null, { reload: true });
                    }, function() {
                        $state.go('designation');
                    })
                }]
            })
            .state('designation.edit', {
                parent: 'designation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/designation/designation-dialog.html',
                        controller: 'DesignationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Designation', function(Designation) {
                                return Designation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('designation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('designation.delete', {
                parent: 'designation',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/designation/designation-delete-dialog.html',
                        controller: 'DesignationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Designation', function(Designation) {
                                return Designation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('designation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
