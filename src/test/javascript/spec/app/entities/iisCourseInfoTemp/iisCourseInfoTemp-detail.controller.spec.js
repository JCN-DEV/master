'use strict';

describe('IisCourseInfoTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockIisCourseInfoTemp, MockIisCurriculumInfo, MockCmsTrade, MockCmsSubject, MockInstitute, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockIisCourseInfoTemp = jasmine.createSpy('MockIisCourseInfoTemp');
        MockIisCurriculumInfo = jasmine.createSpy('MockIisCurriculumInfo');
        MockCmsTrade = jasmine.createSpy('MockCmsTrade');
        MockCmsSubject = jasmine.createSpy('MockCmsSubject');
        MockInstitute = jasmine.createSpy('MockInstitute');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'IisCourseInfoTemp': MockIisCourseInfoTemp,
            'IisCurriculumInfo': MockIisCurriculumInfo,
            'CmsTrade': MockCmsTrade,
            'CmsSubject': MockCmsSubject,
            'Institute': MockInstitute,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("IisCourseInfoTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:iisCourseInfoTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
