'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dearnessAssign', {
                parent: 'entity',
                url: '/dearnessAssigns',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.dearnessAssign.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dearnessAssign/dearnessAssigns.html',
                        controller: 'DearnessAssignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dearnessAssign');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dearnessAssign.detail', {
                parent: 'entity',
                url: '/dearnessAssign/{id}',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.dearnessAssign.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dearnessAssign/dearnessAssign-detail.html',
                        controller: 'DearnessAssignDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dearnessAssign');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DearnessAssign', function($stateParams, DearnessAssign) {
                        return DearnessAssign.get({id : $stateParams.id});
                    }]
                }
            })

            .state('dearnessAssign.new', {
                parent: 'dearnessAssign',
                url: '/new',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                views: {
                    'content@':{
                        templateUrl: 'scripts/app/entities/dearnessAssign/dearnessAssign-dialog.html',
                        controller: 'DearnessAssignDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            amount: null,
                            status: null,
                            createBy: null,
                            createDate: null,
                            updateBy: null,
                            updateDate: null,
                            remarks: null,
                            PayCodeSerial: null,
                            id: null
                        };
                    }
                }
            })
                    .state('dearnessAssign.edit', {
                parent: 'dearnessAssign',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MPOADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dearnessAssign/dearnessAssign-dialog.html',
                        controller: 'DearnessAssignDialogController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'DearnessAssign', function ($stateParams, DearnessAssign) {
                        return DearnessAssign.get({id: $stateParams.id});
                    }]
                }
            })
            /*.state('dearnessAssign.new', {
                parent: 'dearnessAssign',
                url: '/new',
                data: {
                    authorities: ['ROLE_MPOADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dearnessAssign/dearnessAssign-dialog.html',
                        controller: 'DearnessAssignDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    amount: null,
                                    effectiveDate: null,
                                    stopDate: null,
                                    status: null,
                                    createBy: null,
                                    createDate: null,
                                    updateBy: null,
                                    updateDate: null,
                                    remarks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dearnessAssign', null, { reload: true });
                    }, function() {
                        $state.go('dearnessAssign');
                    })
                }]
            })
            .state('dearnessAssign.edit', {
                parent: 'dearnessAssign',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dearnessAssign/dearnessAssign-dialog.html',
                        controller: 'DearnessAssignDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DearnessAssign', function(DearnessAssign) {
                                return DearnessAssign.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dearnessAssign', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
            ;
    });
