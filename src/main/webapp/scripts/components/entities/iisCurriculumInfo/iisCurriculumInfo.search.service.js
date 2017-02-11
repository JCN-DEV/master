'use strict';

angular.module('stepApp')
    .factory('IisCurriculumInfoSearch', function ($resource) {
        return $resource('api/_search/iisCurriculumInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisCurriculumInfoOfCurrentInstitute', function ($resource) {
        return $resource('api/iisCurriculumInfos/currentInstitute', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisCurriculumsOfCurrentInstitute', function ($resource) {
        return $resource('api/iisCurriculumInfos/curriculum/currentInstitute', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisCurriculumsByInstituteAndStatus', function ($resource) {
        return $resource('api/iisCurriculumInfos/curriculums/:instituteId/:status', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisCurriculumsMpoEnlisted', function ($resource) {
        return $resource('api/iisCurriculumInfos/mpoEnlisted', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
