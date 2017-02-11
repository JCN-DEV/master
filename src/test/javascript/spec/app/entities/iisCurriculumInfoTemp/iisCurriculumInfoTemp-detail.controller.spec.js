'use strict';

describe('IisCurriculumInfoTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockIisCurriculumInfoTemp, MockCmsCurriculum, MockInstitute, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockIisCurriculumInfoTemp = jasmine.createSpy('MockIisCurriculumInfoTemp');
        MockCmsCurriculum = jasmine.createSpy('MockCmsCurriculum');
        MockInstitute = jasmine.createSpy('MockInstitute');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'IisCurriculumInfoTemp': MockIisCurriculumInfoTemp,
            'CmsCurriculum': MockCmsCurriculum,
            'Institute': MockInstitute,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("IisCurriculumInfoTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:iisCurriculumInfoTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
