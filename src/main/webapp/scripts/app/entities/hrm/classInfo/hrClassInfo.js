'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrClassInfo', {
                parent: 'hrm',
                url: '/hrClassInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrClassInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/classInfo/hrClassInfos.html',
                        controller: 'HrClassInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrClassInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrClassInfo.detail', {
                parent: 'hrm',
                url: '/hrClassInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrClassInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/classInfo/hrClassInfo-detail.html',
                        controller: 'HrClassInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrClassInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrClassInfo', function($stateParams, HrClassInfo) {
                        return HrClassInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrClassInfo.new', {
                parent: 'hrClassInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/classInfo/hrClassInfo-dialog.html',
                        controller: 'HrClassInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrClassInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            classCode: null,
                            className: null,
                            classDetail: null,
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
            .state('hrClassInfo.edit', {
                parent: 'hrClassInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/classInfo/hrClassInfo-dialog.html',
                        controller: 'HrClassInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrClassInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrClassInfo', function($stateParams, HrClassInfo) {
                        return HrClassInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrClassInfo.delete', {
                parent: 'hrClassInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/classInfo/hrClassInfo-delete-dialog.html',
                        controller: 'HrClassInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrClassInfo', function(HrClassInfo) {
                                return HrClassInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrClassInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
