'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instMemShipTemp', {
                parent: 'entity',
                url: '/instMemShipTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instMemShipTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instMemShipTemp/instMemShipTemps.html',
                        controller: 'InstMemShipTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instMemShipTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instMemShipTemp.detail', {
                parent: 'entity',
                url: '/instMemShipTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instMemShipTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instMemShipTemp/instMemShipTemp-detail.html',
                        controller: 'InstMemShipTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instMemShipTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstMemShipTemp', function($stateParams, InstMemShipTemp) {
                        return InstMemShipTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instMemShipTemp.new', {
                parent: 'instMemShipTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instMemShipTemp/instMemShipTemp-dialog.html',
                        controller: 'InstMemShipTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    fullName: null,
                                    dob: null,
                                    gender: null,
                                    address: null,
                                    email: null,
                                    contact: null,
                                    designation: null,
                                    orgName: null,
                                    orgAdd: null,
                                    orgContact: null,
                                    date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instMemShipTemp', null, { reload: true });
                    }, function() {
                        $state.go('instMemShipTemp');
                    })
                }]
            })
            .state('instMemShipTemp.edit', {
                parent: 'instMemShipTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instMemShipTemp/instMemShipTemp-dialog.html',
                        controller: 'InstMemShipTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstMemShipTemp', function(InstMemShipTemp) {
                                return InstMemShipTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instMemShipTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
