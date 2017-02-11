'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEntertainmentBenefit', {
                parent: 'hrm',
                url: '/hrEntertainmentBenefits',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEntertainmentBenefit.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/entertainmentBenefit/hrEntertainmentBenefits.html',
                        controller: 'HrEntertainmentBenefitController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEntertainmentBenefit');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEntertainmentBenefit.detail', {
                parent: 'hrm',
                url: '/hrEntertainmentBenefit/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEntertainmentBenefit.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/entertainmentBenefit/hrEntertainmentBenefit-detail.html',
                        controller: 'HrEntertainmentBenefitDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEntertainmentBenefit');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEntertainmentBenefit', function($stateParams, HrEntertainmentBenefit) {
                        return HrEntertainmentBenefit.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEntertainmentBenefit.profile', {
                parent: 'hrEntertainmentBenefit',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/entertainmentBenefit/hrEntertainmentBenefit-profile.html',
                        controller: 'HrEntertainmentBenefitProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEntertainmentBenefit');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEntertainmentBenefit.new', {
                parent: 'hrEntertainmentBenefit',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/entertainmentBenefit/hrEntertainmentBenefit-dialog.html',
                        controller: 'HrEntertainmentBenefitDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEntertainmentBenefit');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            eligibilityDate: null,
                            amount: null,
                            totalDays: null,
                            notTakenReason: null,
                            logId:null,
                            logStatus:null,
                            logComments:null,
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
            .state('hrEntertainmentBenefit.edit', {
                parent: 'hrEntertainmentBenefit',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/entertainmentBenefit/hrEntertainmentBenefit-dialog.html',
                        controller: 'HrEntertainmentBenefitDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEntertainmentBenefit');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEntertainmentBenefit', function($stateParams, HrEntertainmentBenefit) {
                        return HrEntertainmentBenefit.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEntertainmentBenefit.delete', {
                parent: 'hrEntertainmentBenefit',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/entertainmentBenefit/hrEntertainmentBenefit-delete-dialog.html',
                        controller: 'HrEntertainmentBenefitDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEntertainmentBenefit', function(HrEntertainmentBenefit) {
                                return HrEntertainmentBenefit.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEntertainmentBenefit', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
