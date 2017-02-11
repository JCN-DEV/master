'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteInfo.instComiteFormation', {
                parent: 'instituteInfo',
                url: '/instComiteSubMenu',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instComiteFormation.home.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instComiteFormation/instComiteSubMenu.html',
                        controller: 'InstComiteFormationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instComiteFormation');
                        $translatePartialLoader.addPart('instMemShip');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('instituteInfo.instComiteFormations', {
                parent: 'instituteInfo',
                url: '/instComiteFormations',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instComiteFormation.home.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instComiteFormation/instComiteFormations.html',
                        controller: 'InstComiteFormationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instComiteFormation');
                        $translatePartialLoader.addPart('instMemShip');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteInfo.instComiteFormation.detail', {
                parent: 'instituteInfo.instComiteFormation',
                url: '/instComiteFormation/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instComiteFormation.detail.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instComiteFormation/instComiteFormation-detail.html',
                        controller: 'InstComiteFormationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instComiteFormation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstComiteFormation', function($stateParams, InstComiteFormation) {
                        return InstComiteFormation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instituteInfo.instComiteFormation.new', {
                parent: 'instituteInfo.instComiteFormation',
                url: '/new',
                data: {
                    authorities: []
                },

                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instComiteFormation/instComiteFormation-dialog.html',
                        controller: 'InstComiteFormationDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            comiteName: null,
                            comiteType: null,
                            address: null,
                            timeFrom: null,
                            timeTo: null,
                            formationDate: null,
                            id: null
                        };
                    }
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instComiteFormation/instComiteFormation-dialog.html',
                        controller: 'InstComiteFormationDialogController',
                        size: 'lg',
                        resolve: {


                            entity: function () {
                                return {
                                    comiteName: null,
                                    comiteType: null,
                                    address: null,
                                    timeFrom: null,
                                    timeTo: null,
                                    formationDate: null,
                                    id: null
                                };
                            }


                        }
                    }).result.then(function(result) {
                        $state.go('instComiteFormation', null, { reload: true });
                    }, function() {
                        $state.go('instComiteFormation');
                    })
                }]*/


            })
            .state('instituteInfo.instComiteFormation.edit', {
                parent: 'instituteInfo.instComiteFormation',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },

                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instComiteFormation/instComiteFormation-dialog.html',
                        controller: 'InstComiteFormationDialogController'
                    }
                },
                resolve: {
                    entity: ['$stateParams','InstComiteFormation', function($stateParams, InstComiteFormation) {
                        return InstComiteFormation.get({id : $stateParams.id});
                    }]
                }


                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instComiteFormation/instComiteFormation-dialog.html',
                        controller: 'InstComiteFormationDialogController',
                        size: 'lg',


                        resolve: {
                            entity: ['InstComiteFormation', function(InstComiteFormation) {
                                return InstComiteFormation.get({id : $stateParams.id});
                            }]
                        }


                    }).result.then(function(result) {
                        $state.go('instComiteFormation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            }).state('instituteInfo.instComiteFormation.addMember', {
                parent: 'instituteInfo.instComiteFormation',
                url: '/{id}/assignMembers',
                data: {
                    authorities: []
                },

                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instComiteFormation/instComiteFormations-assignMembers.html',
                        controller: 'InstComiteFormationAssignMemsController'
                    }
                },
                resolve: {
                    entity: ['$stateParams','InstComiteFormation', function($stateParams, InstComiteFormation) {
                        return InstComiteFormation.get({id : $stateParams.id});
                    }]
                }

            })
            .state('instituteInfo.instComiteFormation.delete', {
                parent: 'instituteInfo.instComiteFormation',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },

                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instComiteFormation/instComiteFormation-delete-dialog.html',
                        controller: 'InstComiteFormationDeleteController',
                        size: 'md',


                        resolve: {
                            entity: ['InstComiteFormation', function(InstComiteFormation) {
                                return InstComiteFormation.get({id : $stateParams.id});
                            }]
                        }


                    }).result.then(function(result) {
                        $state.go('instComiteFormation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]


            });
    });
