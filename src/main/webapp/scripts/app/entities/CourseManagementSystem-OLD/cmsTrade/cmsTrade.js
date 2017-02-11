'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courseInfo.cmsTrade', {
                parent: 'courseInfo',
                url: '/cmsTrades',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsTrade.home.title'
                },
                views: {
                    'courseView@courseInfo': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsTrade/cmsTrades.html',
                        controller: 'CmsTradeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsTrade');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('courseInfo.cmsTrade.detail', {
                parent: 'courseInfo.cmsTrade',
                url: '/cmsTrade/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsTrade.detail.title'
                },
                views: {
                    'courseView@courseInfo': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsTrade/cmsTrade-detail.html',
                        controller: 'CmsTradeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsTrade');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CmsTrade', function($stateParams, CmsTrade) {
                        return CmsTrade.get({id : $stateParams.id});
                    }]
                }
            })
            .state('courseInfo.cmsTrade.new', {
                parent: 'courseInfo.cmsTrade',
                url: '/new',
                data: {
                    authorities: [],
                },
                 views: {
                    'courseView@courseInfo': {
                         templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsTrade/cmsTrade-dialog.html',
                         controller: 'CmsTradeDialogController',
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            code: null,
                            name: null,
                            description: null,
                            status: null,
                            id: null
                        };
                    }
                }

                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsTrade/cmsTrade-dialog.html',
                        controller: 'CmsTradeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cmsTrade', null, { reload: true });
                    }, function() {
                        $state.go('cmsTrade');
                    })
                }]*/
            })
            .state('courseInfo.cmsTrade.edit', {
                parent: 'courseInfo.cmsTrade',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                views: {
                    'courseView@courseInfo': {
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsTrade/cmsTrade-dialog.html',
                        controller: 'CmsTradeDialogController',
                    }
                },
                 resolve: {

                    entity: ['CmsTrade','$stateParams', function(CmsTrade, $stateParams) {

                        return CmsTrade.get({id : $stateParams.id});
                    }]
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsTrade/cmsTrade-dialog.html',
                        controller: 'CmsTradeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CmsTrade', function(CmsTrade) {
                                return CmsTrade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsTrade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('cmsTrade.delete', {
                parent: 'cmsTrade',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsTrade/cmsTrade-delete-dialog.html',
                        controller: 'CmsTradeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CmsTrade', function(CmsTrade) {
                                return CmsTrade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsTrade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
