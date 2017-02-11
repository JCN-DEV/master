'use strict';

describe('JpEmployee Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJpEmployee, MockUser, MockReligion, MockJpAcademicQualification, MockJpEmployeeExperience, MockJpEmploymentHistory, MockJpEmployeeReference, MockJpLanguageProficiency, MockJpEmployeeTraining;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJpEmployee = jasmine.createSpy('MockJpEmployee');
        MockUser = jasmine.createSpy('MockUser');
        MockReligion = jasmine.createSpy('MockReligion');
        MockJpAcademicQualification = jasmine.createSpy('MockJpAcademicQualification');
        MockJpEmployeeExperience = jasmine.createSpy('MockJpEmployeeExperience');
        MockJpEmploymentHistory = jasmine.createSpy('MockJpEmploymentHistory');
        MockJpEmployeeReference = jasmine.createSpy('MockJpEmployeeReference');
        MockJpLanguageProficiency = jasmine.createSpy('MockJpLanguageProficiency');
        MockJpEmployeeTraining = jasmine.createSpy('MockJpEmployeeTraining');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'JpEmployee': MockJpEmployee,
            'User': MockUser,
            'Religion': MockReligion,
            'JpAcademicQualification': MockJpAcademicQualification,
            'JpEmployeeExperience': MockJpEmployeeExperience,
            'JpEmploymentHistory': MockJpEmploymentHistory,
            'JpEmployeeReference': MockJpEmployeeReference,
            'JpLanguageProficiency': MockJpLanguageProficiency,
            'JpEmployeeTraining': MockJpEmployeeTraining
        };
        createController = function() {
            $injector.get('$controller')("JpEmployeeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jpEmployeeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
