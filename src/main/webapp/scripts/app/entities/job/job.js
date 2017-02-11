'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('job', {
                parent: 'entity',
                url: '/jobs',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN','ROLE_JOBSEEKER'],
                    pageTitle: 'stepApp.job.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/job/jobs.html',
                        controller: 'JobController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('jobStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            }).state('job-archive', {
                parent: 'entity',
                url: '/job-archive',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_JPADMIN','ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.job.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/job/jobs.html',
                        controller: 'JobController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('jobStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('job.detail', {
                parent: 'entity',
                url: '/job/{id}',
                data: {
                    pageTitle: 'stepApp.job.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/job/job-detail.html',
                        controller: 'JobDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('jobStatus');
                        $translatePartialLoader.addPart('employer');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Job', function($stateParams, Job) {
                        return Job.get({id : $stateParams.id});
                    }]
                }
            })
            /*.state('job.applied', {
                parent: 'employer.job-list',
                url: '/job-application/{id}',
                data: {
                    authorities: ['ROLE_EMPLOYER'],
                    pageTitle: 'stepApp.job.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/job/job-application.html',
                        controller: 'JobApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        return $translate.refresh();detail
                    }],
                    entity: ['$stateParams', 'JobApplication', function($stateParams, JobApplication) {
                        return JobApplication.get({id : $stateParams.id});
                    }]
                }
            })*/
            .state('job.new', {
                parent: 'job',
                url: '/new',
                data: {
                    authorities: ['ROLE_EMPLOYER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/job/job-dialog.html',
                        controller: 'JobDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    type: null,
                                    minimumSalary: null,
                                    maximumSalary: null,
                                    vacancy: null,
                                    description: null,
                                    benefits: null,
                                    educationRequirements: null,
                                    experienceRequirements: null,
                                    otherRequirements: null,
                                    publishedAt: null,
                                    applicationDeadline: null,
                                    location: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('job', null, { reload: true });
                    }, function() {
                        $state.go('job');
                    })
                }]
            }).state('mainApply', {
                parent: 'site',
                url: '/{id}/apply',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.new-job'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/apply-dialog.html',
                        controller: 'ApplyDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jobStatus');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('employer');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Job', function($stateParams, Job) {
                        return Job.get({id : $stateParams.id});
                    }]
                }
            })

            .state('job.edit', {
                parent: 'job',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_EMPLOYER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/job/job-dialog.html',
                        controller: 'JobDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Job', function(Job) {
                                return Job.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('employer.job-list');
                    }, function() {
                            $state.go('employer.job-list');
                    })
                }]
            })
            .state('job.delete', {
                parent: 'job',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_EMPLOYER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/job/job-delete-dialog.html',
                        controller: 'JobDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Job', function(Job) {
                                return Job.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('employer.job-list');
                    }, function() {
                            $state.go('employer.job-list');
                    })
                }]
            })
            .state('job.create-job', {
                            parent: 'job',
                            url: '/create-job',
                            data: {
                                authorities: ['ROLE_EMPLOYER'],
                                pageTitle: 'global.menu.account.create-job'
                            },
                            views: {
                                'content@': {
                                    templateUrl: 'scripts/app/entities/job/create-job.html',
                                    controller: 'CreateJobController'
                                }
                            },
                            resolve: {
                                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                    $translatePartialLoader.addPart('settings');
                                    $translatePartialLoader.addPart('job');
                                    $translatePartialLoader.addPart('cat');
                                    $translatePartialLoader.addPart('employer');
                                    $translatePartialLoader.addPart('country');
                                    $translatePartialLoader.addPart('global');
                                     return $translate.refresh();
                                }]
                            }
            });
    });
