'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmplTypeInfo', {
                parent: 'hrm',
                url: '/hrEmplTypeInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmplTypeInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/emplTypeInfo/hrEmplTypeInfos.html',
                        controller: 'HrEmplTypeInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmplTypeInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmplTypeInfo.detail', {
                parent: 'hrEmplTypeInfo',
                url: '/hrEmplTypeInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmplTypeInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/emplTypeInfo/hrEmplTypeInfo-detail.html',
                        controller: 'HrEmplTypeInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmplTypeInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmplTypeInfo', function($stateParams, HrEmplTypeInfo) {
                        return HrEmplTypeInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmplTypeInfo.new', {
                parent: 'hrEmplTypeInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/emplTypeInfo/hrEmplTypeInfo-dialog.html',
                        controller: 'HrEmplTypeInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmplTypeInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            typeCode: null,
                            typeName: null,
                            typeDetail: null,
                            activeStatus: true,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('hrEmplTypeInfo.edit', {
                parent: 'hrEmplTypeInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/emplTypeInfo/hrEmplTypeInfo-dialog.html',
                        controller: 'HrEmplTypeInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmplTypeInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmplTypeInfo', function($stateParams, HrEmplTypeInfo) {
                        return HrEmplTypeInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmplTypeInfo.delete', {
                parent: 'hrEmplTypeInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/emplTypeInfo/hrEmplTypeInfo-delete-dialog.html',
                        controller: 'HrEmplTypeInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmplTypeInfo', function(HrEmplTypeInfo) {
                                return HrEmplTypeInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmplTypeInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
