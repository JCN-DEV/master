'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            /*.state('insAcademicInfo', {
                parent: 'entity',
                url: '/insAcademicInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.insAcademicInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/insAcademicInfo/insAcademicInfos.html',
                        controller: 'InsAcademicInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('insAcademicInfo');
                        $translatePartialLoader.addPart('curriculum');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })*/
            .state('instGenInfo.insAcademicInfo.detail', {
                parent: 'instGenInfo',
                url: '/ins-Academic-Info/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.insAcademicInfo.detail.title'
                },
                views: {
                   'instituteView@instGenInfo':{
                             templateUrl: 'scripts/app/entities/insAcademicInfo/insAcademicInfo-detail.html',
                             controller: 'InsAcademicInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('insAcademicInfo');
                        $translatePartialLoader.addPart('curriculum');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InsAcademicInfo', function($stateParams, InsAcademicInfo) {
                        return InsAcademicInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instGenInfo.insAcademicInfo.new', {
                parent: 'instGenInfo',
                url: '/new-academic-info',
                data: {
                    //authorities: ['ROLE_USER'],
                    authorities: [],
                },
                views: {
                   'instituteView@instGenInfo':{
                           templateUrl: 'scripts/app/entities/insAcademicInfo/insAcademicInfo-dialog.html',
                           controller: 'InsAcademicInfoDialogController'
                    }
                },
                resolve: {translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                                  $translatePartialLoader.addPart('insAcademicInfo');
                                                  $translatePartialLoader.addPart('curriculum');
                                                  return $translate.refresh();
                                              }],


                    entity: function () {
                        return {
                            counselorName: null,
                            curriculum: null,
                            totalTechTradeNo: null,
                            tradeTechDetails: null,
                            status: null,
                            id: null
                        };
                    }
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/insAcademicInfo/insAcademicInfo-dialog.html',
                        controller: 'InsAcademicInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    counselorName: null,
                                    curriculum: null,
                                    totalTechTradeNo: null,
                                    tradeTechDetails: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.insAcademicInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.insAcademicInfo');
                    })
                }]*/
            })
            .state('instGenInfo.insAcademicInfo.edit', {
                parent: 'instGenInfo',
                url: '/{id}/edit-academic-info',
                data: {
                    authorities: []
                },

                views: {
                    'instituteView@instGenInfo':{
                        templateUrl: 'scripts/app/entities/insAcademicInfo/insAcademicInfo-dialog.html',
                        controller: 'InsAcademicInfoDialogController'
                    }
                },
                resolve: {translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('insAcademicInfo');
                    $translatePartialLoader.addPart('curriculum');
                    return $translate.refresh();
                }],

                    entity: ['InsAcademicInfo','$stateParams', function(InsAcademicInfo,$stateParams) {
                        return InsAcademicInfo.get({id : $stateParams.id});
                    }]
                }

            })
            .state('instGenInfo.insAcademicInfo.delete', {
                parent: 'instGenInfo',
                url: '/{id}/delete-academic-info',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/insAcademicInfo/insAcademicInfo-delete-dialog.html',
                        controller: 'InsAcademicInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InsAcademicInfo', function(InsAcademicInfo) {
                                return InsAcademicInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.insAcademicInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.insAcademicInfo');
                    })
                }]
            })
            .state('instGenInfo.insAcademicInfo.approve', {
                parent: 'instGenInfo',
                url: '/{id}/approve-Academic-Info',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/insAcademicInfo/insAcademicInfo-approve-dialog.html',
                        controller: 'InsAcademicInfoApproveController',
                        size: 'md',
                        resolve: {
                            entity: ['InsAcademicInfo', function(InsAcademicInfo) {
                                return InsAcademicInfo.get({id : $stateParams.id});
                              //  return InstGenInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            console.log(result);
                            $state.go('instGenInfo.insAcademicInfo', null, { reload: true });
                        }, function() {
                            $state.go('instGenInfo.insAcademicInfo');
                        })

                }]

            });
    });
