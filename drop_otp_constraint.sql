-- SQL Script to drop the unique constraint on otps.email column
-- Run this script in your MySQL database if you get constraint violation errors

-- Drop the unique constraint
ALTER TABLE otps DROP INDEX UKg9l08tfsgxhbo1vsrms3s6tvq;

-- Verify the constraint is removed
SHOW INDEXES FROM otps WHERE Column_name = 'email';

