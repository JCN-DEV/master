'use strict';

describe('InstEmployee Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstEmployee, MockInstitute, MockDesignation, MockReligion, MockQuota, MockCourseTech, MockGradeSetup, MockInstEmpAddress, MockInstEmpEduQuali, MockInstEmplHist;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        MockInstitute = jasmine.createSpy('MockInstitute');
        MockDesignation = jasmine.createSpy('MockDesignation');
        MockReligion = jasmine.createSpy('MockReligion');
        MockQuota = jasmine.createSpy('MockQuota');
        MockCourseTech = jasmine.createSpy('MockCourseTech');
        MockGradeSetup = jasmine.createSpy('MockGradeSetup');
        MockInstEmpAddress = jasmine.createSpy('MockInstEmpAddress');
        MockInstEmpEduQuali = jasmine.createSpy('MockInstEmpEduQuali');
        MockInstEmplHist = jasmine.createSpy('MockInstEmplHist');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstEmployee': MockInstEmployee,
            'Institute': MockInstitute,
            'Designation': MockDesignation,
            'Religion': MockReligion,
            'Quota': MockQuota,
            'CourseTech': MockCourseTech,
            'GradeSetup': MockGradeSetup,
            'InstEmpAddress': MockInstEmpAddress,
            'InstEmpEduQuali': MockInstEmpEduQuali,
            'InstEmplHist': MockInstEmplHist
        };
        createController = function() {
            $injector.get('$controller')("InstEmployeeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instEmployeeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
