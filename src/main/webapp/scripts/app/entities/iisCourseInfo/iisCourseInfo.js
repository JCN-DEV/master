'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('iisCourseInfo', {
                parent: 'entity',
                url: '/iisCourseInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.iisCourseInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/iisCourseInfo/iisCourseInfos.html',
                        controller: 'IisCourseInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCourseInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('iisCourseInfo.detail', {
                parent: 'entity',
                url: '/iisCourseInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.iisCourseInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/iisCourseInfo/iisCourseInfo-detail.html',
                        controller: 'IisCourseInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCourseInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'IisCourseInfo', function($stateParams, IisCourseInfo) {
                        return IisCourseInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('iisCourseInfo.new', {
                parent: 'iisCourseInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/iisCourseInfo/iisCourseInfo-dialog.html',
                        controller: 'IisCourseInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    perDateEdu: null,
                                    perDateBteb: null,
                                    mpoEnlisted: null,
                                    dateMpo: null,
                                    seatNo: null,
                                    shift: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('iisCourseInfo', null, { reload: true });
                    }, function() {
                        $state.go('iisCourseInfo');
                    })
                }]
            })
            .state('iisCourseInfo.edit', {
                parent: 'iisCourseInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/iisCourseInfo/iisCourseInfo-dialog.html',
                        controller: 'IisCourseInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['IisCourseInfo', function(IisCourseInfo) {
                                return IisCourseInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('iisCourseInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('iisCourseInfo.delete', {
                parent: 'iisCourseInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/iisCourseInfo/iisCourseInfo-delete-dialog.html',
                        controller: 'IisCourseInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['IisCourseInfo', function(IisCourseInfo) {
                                return IisCourseInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('iisCourseInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
