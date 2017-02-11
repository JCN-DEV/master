'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteInfo.instMemShip', {
                parent: 'instituteInfo',
                url: '/instMemShips',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instMemShip.home.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instMemShip/instMemShips.html',
                        controller: 'InstMemShipController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instMemShip');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteInfo.instMemShip.detail', {
                parent: 'instituteInfo.instMemShip',
                url: '/instMemShip/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instMemShip.detail.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instMemShip/instMemShip-detail.html',
                        controller: 'InstMemShipDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instMemShip');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstMemShip', function($stateParams, InstMemShip) {
                        return InstMemShip.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instituteInfo.instMemShip.new', {
                parent: 'instituteInfo.instMemShip',
                url: '/new',
                data: {
                    authorities: []
                },

                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instMemShip/instMemShip-dialog.html',
                        controller: 'InstMemShipDialogController',
                        size: 'lg'
                    }
                },
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

                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instMemShip/instMemShip-dialog.html',
                        controller: 'InstMemShipDialogController',
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
                        $state.go('instMemShip', null, { reload: true });
                    }, function() {
                        $state.go('instMemShip');
                    })
                }]*/
            })
            .state('instituteInfo.instMemShip.edit', {
                parent: 'instituteInfo.instMemShip',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instMemShip/instMemShip-dialog.html',
                        controller: 'InstMemShipDialogController'

                    }
                },
                resolve: {
                    entity: ['$stateParams', 'InstMemShip', function($stateParams, InstMemShip) {
                        return InstMemShip.get({id : $stateParams.id});
                    }]
                }

               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instMemShip/instMemShip-dialog.html',
                        controller: 'InstMemShipDialogController',
                        size: 'lg',


                        resolve: {
                            entity: ['InstMemShip', function(InstMemShip) {
                                return InstMemShip.get({id : $stateParams.id});
                            }]
                        }


                    }).result.then(function(result) {
                        $state.go('instMemShip', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/


            })
            .state('instituteInfo.instMemShip.delete', {
                parent: 'instituteInfo.instMemShip',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instMemShip/instMemShip-delete-dialog.html',
                        controller: 'InstMemShipDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstMemShip', function(InstMemShip) {
                                return InstMemShip.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instMemShip', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
