'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteInfo.iisCurriculumInfo', {
                parent: 'instituteInfo',
                url: '/curriculumInfo-list',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.iisCurriculumInfo.home.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/iisCurriculumInfo/iisCurriculumInfos.html',
                        controller: 'IisCurriculumInfoController'

                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCurriculumInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteInfo.iisCurriculumInfo.new', {
                parent: 'instituteInfo.iisCurriculumInfo',
                url: '/new',
                data: {
                    authorities: []
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/iisCurriculumInfo/iisCurriculumInfo-dialog.html',
                        controller: 'IisCurriculumInfoDialogController',
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            firstDate: null,
                            lastDate: null,
                            mpoEnlisted: null,
                            recNo: null,
                            mpoDate: null,
                            id: null
                        };
                    }
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/iisCurriculumInfo/iisCurriculumInfo-dialog.html',
                        controller: 'IisCurriculumInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    firstDate: null,
                                    lastDate: null,
                                    mpoEnlisted: null,
                                    recNo: null,
                                    mpoDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('iisCurriculumInfo', null, { reload: true });
                    }, function() {
                        $state.go('iisCurriculumInfo');
                    })
                }]*/
            })
            .state('instituteInfo.iisCurriculumInfo.detail', {
                parent: 'instituteInfo.iisCurriculumInfo',
                url: 'curriculumInfo-detail/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.iisCurriculumInfo.detail.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/iisCurriculumInfo/iisCurriculumInfo-detail.html',
                        controller: 'IisCurriculumInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCurriculumInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'IisCurriculumInfoTemp', function($stateParams, IisCurriculumInfoTemp) {
                        return IisCurriculumInfoTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instituteInfo.iisCurriculumInfo.edit', {
                parent: 'instituteInfo.iisCurriculumInfo',
                url: '/{id}/edit',

                data: {
                    authorities: [],
                    pageTitle: 'stepApp.iisCurriculumInfo.home.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/iisCurriculumInfo/iisCurriculumInfo-dialog.html',
                        controller: 'IisCurriculumInfoDialogController',
                        size: 'lg'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCurriculumInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'IisCurriculumInfoTemp', function($stateParams, IisCurriculumInfoTemp) {
                        return IisCurriculumInfoTemp.get({id : $stateParams.id});
                    }]
                }

                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/iisCurriculumInfo/iisCurriculumInfo-dialog.html',
                        controller: 'IisCurriculumInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['IisCurriculumInfo', function(IisCurriculumInfo) {
                                return IisCurriculumInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('iisCurriculumInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('instituteInfo.iisCurriculumInfo.delete', {
                parent: 'instituteInfo.iisCurriculumInfo',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/iisCurriculumInfo/iisCurriculumInfo-delete-dialog.html',
                        controller: 'IisCurriculumInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['IisCurriculumInfo', function(IisCurriculumInfo) {
                                return IisCurriculumInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteInfo.iisCurriculumInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
        .state('instituteInfo.iisCurriculumInfo.approve', {
            parent: 'instituteInfo.approve',
            url: '/curriculum-info-approve/{instid}',
            data: {
                authorities: [],
            },
            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                $modal.open({
                    templateUrl: 'scripts/app/entities/instituteInformationSystem/iisCurriculumInfo/iisCurriculumInfo-approve-dialog.html',
                    controller: 'IisCurriculumInfoApproveController',
                    size: 'md',
                    resolve: {
                        entity: ['InstFinancialInfo', function(InstFinancialInfo) {
                            return InstFinancialInfo.get({id : $stateParams.instid});
                            //  return InstGenInfo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function(result) {
                        console.log(result);
                        $state.go('instituteInfo.approve',{},{reload:true});
                    }, function() {
                        $state.go('instituteInfo.approve',{},{reload:true});
                    })

            }]

        })
            .state('instituteInfo.iisCurriculumInfo.decline', {
                parent: 'instituteInfo.approve',
                url: '/curriculum-info-decline/{instid}',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-decline.html',
                        controller: 'InstFinancialInfoDeclineController',
                        size: 'md'
                    }).result.then(function(result) {
                            console.log(result);
                            $state.go('instituteInfo.approve',{},{reload:true});
                        }, function() {
                            $state.go('instituteInfo.approve',{},{reload:true});
                        })

                }]

            });
    });
