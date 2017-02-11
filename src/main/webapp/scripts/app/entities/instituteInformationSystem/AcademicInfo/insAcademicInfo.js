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
            .state('instituteInfo.academicInfo', {
                parent: 'instituteInfo',
                url: '/academic-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.insAcademicInfo.detail.title'
                },
                views: {
                   'instituteView@instituteInfo':{
                             templateUrl: 'scripts/app/entities/instituteInformationSystem/AcademicInfo/insAcademicInfo-detail.html',
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
                           // console.log(InsAcademicInfo.get({id:0}));
                        return InsAcademicInfo.get({id:0});
                        //return  InsAcademicInfo.get({id:$stateParams.id});
                    }]

                }
                /*onEnter: ['$stateParams', '$state', '$modal','InsAcademicInfo','entity', function($stateParams, $state, $modal,InsAcademicInfo,entity) {
                }]*/


            })
            .state('instituteInfo.academicInfo.new', {
                parent: 'instituteInfo.academicInfo',
                url: '/new',
                data: {
                    //authorities: ['ROLE_USER'],
                    authorities: [],
                },
                views: {
                   'instituteView@instituteInfo':{
                           templateUrl: 'scripts/app/entities/instituteInformationSystem/AcademicInfo/insAcademicInfo-dialog.html',
                           controller: 'InsAcademicInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
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
            .state('instituteInfo.academicInfo.edit', {
                parent: 'instituteInfo.academicInfo',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },

                views: {
                    'instituteView@instituteInfo':{
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/AcademicInfo/insAcademicInfo-dialog.html',
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
            .state('instituteInfo.academicInfo.delete', {
                parent: 'instituteInfo.academicInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/AcademicInfo/insAcademicInfo-delete-dialog.html',
                        controller: 'InsAcademicInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InsAcademicInfo', function(InsAcademicInfo) {
                                return InsAcademicInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteInfo.academicInfo', null, { reload: true });
                    }, function() {
                        $state.go('instituteInfo.academicInfo');
                    })
                }]
            })
            .state('instituteInfo.academicInfo.approve', {
                parent: 'instituteInfo.approve',
                url: '/academic-info-approve/{acaid}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/AcademicInfo/insAcademicInfo-approve-dialog.html',
                        controller: 'InsAcademicInfoApproveController',
                        size: 'md',
                        resolve: {

                        }
                    }).result.then(function(result) {
                            console.log(result);
                            $state.go('instituteInfo.approve',{},{reload:true});
                        }, function() {
                            $state.go('instituteInfo.approve',{},{reload:true});
                        })

                }]

            })
            .state('instituteInfo.academicInfo.decline', {
                parent: 'instituteInfo.approve',
                url: '/{id}/academic-info-decline',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-decline.html',
                        controller: 'InsAcademicInfoDeclineController',
                        size: 'md',
                        resolve: {

                        }
                    }).result.then(function(result) {
                            console.log(result);
                            $state.go('instituteInfo.approve',{},{reload:true});
                        }, function() {
                            $state.go('instituteInfo.approve',{},{reload:true});
                        })

                }]

            });



    });
