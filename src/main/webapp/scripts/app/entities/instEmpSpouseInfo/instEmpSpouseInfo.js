'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instEmpSpouseInfo', {
                parent: 'entity',
                url: '/instEmpSpouseInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmpSpouseInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmpSpouseInfo/instEmpSpouseInfos.html',
                        controller: 'InstEmpSpouseInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpSpouseInfo');
                        $translatePartialLoader.addPart('relationType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instEmpSpouseInfo.detail', {
                parent: 'entity',
                url: '/instEmpSpouseInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmpSpouseInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmpSpouseInfo/instEmpSpouseInfo-detail.html',
                        controller: 'InstEmpSpouseInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpSpouseInfo');
                        $translatePartialLoader.addPart('relationType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmpSpouseInfo', function($stateParams, InstEmpSpouseInfo) {
                        return InstEmpSpouseInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instEmpSpouseInfo.new', {
                parent: 'instEmpSpouseInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpSpouseInfo/instEmpSpouseInfo-dialog.html',
                        controller: 'InstEmpSpouseInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    dob: null,
                                    isNominee: false,
                                    gender: null,
                                    relation: null,
                                    nomineePercentage: null,
                                    occupation: null,
                                    tin: null,
                                    nid: null,
                                    designation: null,
                                    govJobId: null,
                                    mobile: null,
                                    officeContact: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpSpouseInfo', null, { reload: true });
                    }, function() {
                        $state.go('instEmpSpouseInfo');
                    })
                }]
            })
            .state('instEmpSpouseInfo.edit', {
                parent: 'instEmpSpouseInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpSpouseInfo/instEmpSpouseInfo-dialog.html',
                        controller: 'InstEmpSpouseInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmpSpouseInfo', function(InstEmpSpouseInfo) {
                                return InstEmpSpouseInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpSpouseInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instEmpSpouseInfo.delete', {
                parent: 'instEmpSpouseInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmpSpouseInfo/instEmpSpouseInfo-delete-dialog.html',
                        controller: 'InstEmpSpouseInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmpSpouseInfo', function(InstEmpSpouseInfo) {
                                return InstEmpSpouseInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmpSpouseInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
