'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instGenInfo', {
                parent: 'entity',
                url: '/institute-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instGenInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instGenInfo/instGenInfos.html',
                        controller: 'InstGenInfoController'
                    },
                    'instituteView@instGenInfo':{
                          templateUrl: 'scripts/app/entities/instGenInfo/institute-dashboard.html',
                          controller: 'InstGenInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instGenInfo.detail', {
                parent: 'instGenInfo',
                url: '/inst-Gen-Info/',
                data: {
                   // authorities: ['ROLE_USER'],
                    authorities: [],
                    pageTitle: 'stepApp.instGenInfo.detail.title'
                },
                views: {
                    'instituteView@instGenInfo': {
                        templateUrl: 'scripts/app/entities/instGenInfo/instGenInfo-detail.html',
                        controller: 'InstGenInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstGenInfo', function($stateParams, InstGenInfo) {
                        return InstGenInfo.get({id : 0});
                    }]
                }
            })
            .state('instGenInfo.new', {
                parent: 'instGenInfo',
                url: '/new-General-Info',
                data: {
                    authorities: [],
                },
                views: {
                    'instituteView@instGenInfo': {
                        templateUrl:'scripts/app/entities/instGenInfo/instGenInfo-dialog.html',
                        controller: 'InstGenInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            code: null,
                            name: null,
                            publicationDate: null,
                            type: null,
                            village: null,
                            postOffice: null,
                            postCode: null,
                            landPhone: null,
                            mobileNo: null,
                            email: null,
                            consArea: null,
                            id: null
                        };
                    }
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGenInfo/instGenInfo-dialog.html',
                        controller: 'InstGenInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    publicationDate: null,
                                    type: null,
                                    village: null,
                                    postOffice: null,
                                    postCode: null,
                                    landPhone: null,
                                    mobileNo: null,
                                    email: null,
                                    consArea: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo');
                    })
                }]*/
            })
            .state('instGenInfo.edit', {
                parent: 'instGenInfo',
                url: '/{id}/edit-General-Info',
                data: {
                    authorities: [],
                },
                 views: {
                    'instituteView@instGenInfo': {
                        templateUrl: 'scripts/app/entities/instGenInfo/instGenInfo-dialog.html',
                        controller: 'InstGenInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['InstGenInfo','$stateParams', function(InstGenInfo,$stateParams) {
                        return InstGenInfo.get({id : $stateParams.id});
                    }]

                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGenInfo/instGenInfo-dialog.html',
                        controller: 'InstGenInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstGenInfo', function(InstGenInfo) {
                                return InstGenInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('instGenInfo.delete', {
                parent: 'instGenInfo',
                url: '/{id}/delete-General-Info',
                data: {
                    authorities: [],
                   // authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGenInfo/instGenInfo-delete-dialog.html',
                        controller: 'InstGenInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstGenInfo', function(InstGenInfo) {
                                return InstGenInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.generalInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.generalInfo');
                    })
                }]

            })
            .state('instGenInfo.approve', {
                parent: 'instGenInfo',
                url: '/{id}/approve-General-Info',
                data: {
                   // authorities: ['ROLE_ADMIN'],
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGenInfo/instGenInfo-approve-dialog.html',
                        controller: 'InstGenInfoApproveController',
                        size: 'md',
                        resolve: {
                            entity: ['InstGenInfo', function(InstGenInfo) {
                                return InstGenInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        console.log(result);
                        $state.go('instGenInfo.generalInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.generalInfo');
                    })

                }]

            })
            .state('instGenInfo.generalInfo',{
                parent: 'instGenInfo',
                url: '/general-info',
                data: {
                   // authorities: ['ROLE_USER']
                    authorities: []
                },
                views: {
                    'instituteView@instGenInfo':{
                          templateUrl: 'scripts/app/entities/instGenInfo/institute-list.html',
                          controller: 'InstGenInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
           .state('instGenInfo.insAcademicInfo',{
                parent: 'instGenInfo',
                url: '/academic-info',
                data: {
                   // authorities: ['ROLE_USER']
                    authorities: []
                },
                views: {
                    'instituteView@instGenInfo':{
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
            })
            .state('instGenInfo.instAdmInfo',{
                parent: 'instGenInfo',
                url: '/administrative-info',
                data: {
                    //authorities: ['ROLE_USER']
                    authorities: []
                },
                views: {
                    'instituteView@instGenInfo':{
                          templateUrl: 'scripts/app/entities/instAdmInfo/instAdmInfos.html',
                          controller: 'InstAdmInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instAdmInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instGenInfo.instFinancialInfo',{
                parent: 'instGenInfo',
                url: '/financial-Info',
                data: {
                   // authorities: ['ROLE_USER']
                    authorities: []
                },
                views: {
                    'instituteView@instGenInfo':{
                          templateUrl: 'scripts/app/entities/instFinancialInfo/instFinancialInfos.html',
                          controller: 'InstFinancialInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instFinancialInfo');
                        $translatePartialLoader.addPart('accountType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instGenInfo.instEmpCount',{
                parent: 'instGenInfo',
                url: '/stuff-info',
                data: {
                    //authorities: ['ROLE_USER']
                    authorities: []
                },
                views: {
                    'instituteView@instGenInfo':{
                            templateUrl: 'scripts/app/entities/instEmpCount/instEmpCounts.html',
                            controller: 'InstEmpCountController'
                    }
                },
                resolve: {
                    /*translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]*/
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmpCount');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instGenInfo.instInfraInfo',{
                parent: 'instGenInfo',
                url: '/infrastructure-info',
                data: {
                    //authorities: ['ROLE_USER']
                    authorities: []
                },
                views: {
                    'instituteView@instGenInfo':{
                          templateUrl: 'scripts/app/entities/instInfraInfo/instInfraInfos.html',
                          controller: 'InstInfraInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                       $translatePartialLoader.addPart('instInfraInfo');
                       $translatePartialLoader.addPart('instBuilding');
                       $translatePartialLoader.addPart('instLand');
                       $translatePartialLoader.addPart('global');
                       return $translate.refresh();
                   }]

                }
            })
            .state('instGenInfo.report',{
                parent: 'instGenInfo',
                url: '/report',
                data: {
                    //authorities: ['ROLE_USER']
                    authorities: []
                },
                views: {
                    'instituteView@instGenInfo':{
                          templateUrl: 'scripts/app/entities/instGenInfo/institute-dashboard.html',
                          controller: 'InstGenInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instGenInfo.dashboard',{
                parent: 'instGenInfo',
                url: '/dashboard',
                data: {
                    //authorities: ['ROLE_USER']
                    authorities: []
                },
                views: {
                    'instituteView@instGenInfo':{
                          templateUrl: 'scripts/app/entities/instGenInfo/institute-dashboard.html',
                          controller: 'InstGenInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            }).state('instGenInfo.newInstitute', {
                parent: 'instGenInfo',
                url: '/institute-General-Info',
                data: {
                    authorities: [],
                },
                views: {
                    'content@': {
                        templateUrl:'scripts/app/entities/instGenInfo/instGenInfo-dialog.html',
                        controller: 'InstGenInfoPublicController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            code: null,
                            name: null,
                            publicationDate: null,
                            type: null,
                            village: null,
                            postOffice: null,
                            postCode: null,
                            landPhone: null,
                            mobileNo: null,
                            email: null,
                            consArea: null,
                            id: null
                        };
                    }
                }

            })

    });
