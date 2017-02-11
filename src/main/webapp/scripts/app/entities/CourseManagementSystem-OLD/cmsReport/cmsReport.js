'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider

            .state('courseInfo.report', {
                parent: 'courseInfo',
                url: '/report',
/*
                url: '/report/{id}',
*/

                data: {
                    authorities: [],
                    pageTitle: 'stepApp.cmsCurriculum.detail.title'
                },
               views: {
                        'courseView@courseInfo':{
                           templateUrl: 'scripts/app/entities/CourseManagementSystem/cmsReport/cmsReport.html',
                           controller: 'CmsReportController'
                        }
                     },
           /* resolve:
            {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cmsReport');
                    return $translate.refresh();
                }],
               entity: ['$stateParams', 'CmsReport', function($stateParams, CmsReport) {
                    return CmsReport.get({id : $stateParams.id});
                }]
            }*/
            })


    });
